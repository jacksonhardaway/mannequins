package dev.hardaway.mannequins.common.block;

import com.mojang.serialization.MapCodec;
import dev.hardaway.mannequins.common.block.entity.DummyBlockEntity;
import dev.hardaway.mannequins.common.block.entity.MannequinBlockEntity;
import dev.hardaway.mannequins.common.menu.DummyEditorMenu;
import dev.hardaway.mannequins.common.network.payload.ClientboundAttackMannequinPayload;
import dev.hardaway.mannequins.core.Mannequins;
import dev.hardaway.mannequins.core.registry.MannequinsBlockEntities;
import dev.hardaway.mannequins.core.registry.MannequinsMenus;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.neoforged.neoforge.client.event.RenderHighlightEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.util.TriState;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class MannequinBlock extends DummyBlock {
    public static final MapCodec<DummyBlock> CODEC = simpleCodec(MannequinBlock::new);
    public static final ResourceLocation MANNEQUIN_TEXTURE = Mannequins.path("textures/block/mannequin.png");

    private static final VoxelShape BASE_SHAPE = Shapes.join(Shapes.empty(), Shapes.box(0.125, 0, 0.125, 0.875, 0.1875, 0.875), BooleanOp.OR);
    protected static final VoxelShape SHAPE = Shapes.join(Shapes.empty(), Shapes.box(0, 0, 0.125, 1, 1, 0.875), BooleanOp.OR);

    public MannequinBlock(Properties properties) {
        super(properties);
        NeoForge.EVENT_BUS.addListener(this::onLeftClick);
        NeoForge.EVENT_BUS.addListener(this::onHighlight);
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int id, Inventory inventory, ContainerLevelAccess access, DummyBlockEntity dummy) {
        return new DummyEditorMenu(MannequinsMenus.MANNEQUIN, id, inventory, access, dummy);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public ResourceLocation getDummyTexture() {
        return MANNEQUIN_TEXTURE;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return state.getValue(HALF) == DoubleBlockHalf.LOWER ? new MannequinBlockEntity(pos, state) : null;
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return state.getValue(HALF) == DoubleBlockHalf.LOWER ? BASE_SHAPE : SHAPE;
    }

    @Override
    protected void onProjectileHit(Level level, BlockState state, BlockHitResult hit, Projectile projectile) {
        if (level.isClientSide())
            return;

        BlockPos pos = hit.getBlockPos();
        Vec3 centerPos = hit.getBlockPos().getBottomCenter();

        Entity owner = projectile.getOwner();
        Vec3 attackPos = Objects.requireNonNullElse(owner, projectile).position();

        PacketDistributor.sendToPlayersTrackingChunk((ServerLevel) level, new ChunkPos(pos),
                new ClientboundAttackMannequinPayload(pos, (float) (Math.PI - Mth.atan2(attackPos.x() - centerPos.x(), attackPos.z() - centerPos.z()))));
    }

    @Override
    public boolean onDestroyedByPlayer(BlockState state, Level level, BlockPos pos, Player player, boolean willHarvest, FluidState fluid) {
        if (state.getValue(DummyBlock.HALF) != DoubleBlockHalf.LOWER)
            return false;

        return super.onDestroyedByPlayer(state, level, pos, player, willHarvest, fluid);
    }

    private void onLeftClick(PlayerInteractEvent.LeftClickBlock event) {
        Level level = event.getLevel();

        BlockPos pos = event.getPos();
        BlockState state = level.getBlockState(pos);
        if (!state.is(this) || state.getValue(DummyBlock.HALF) == DoubleBlockHalf.LOWER)
            return;

        Player player = event.getEntity();
        Vec3 centerPos = pos.getBottomCenter();
        float attackYaw = (float) -Mth.atan2(player.getX() - centerPos.x(), player.getZ() - centerPos.z());
        // TODO check bounds
        event.setCanceled(true);
        event.setUseBlock(TriState.FALSE);
        event.setUseItem(TriState.FALSE);

        if (level.isClientSide())
            return;

        if (level.getBlockEntity(pos.below()) instanceof MannequinBlockEntity mannequin) {
            if (mannequin.attackDelay > 0)
                return;

            mannequin.attackDelay = 5;
        }

        PacketDistributor.sendToPlayersTrackingChunk((ServerLevel) level, new ChunkPos(pos), new ClientboundAttackMannequinPayload(pos, attackYaw));
    }

    private void onHighlight(RenderHighlightEvent.Block event) {
        BlockHitResult result = event.getTarget();
        BlockPos pos = result.getBlockPos();
        Level level = event.getCamera().getEntity().level();
        BlockState state = level.getBlockState(pos);
        if (!state.is(this) || state.getValue(DummyBlock.HALF) == DoubleBlockHalf.LOWER)
            return;

        event.setCanceled(true);
        // TODO check bounds
    }

    @Override
    public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> blockEntityType) {
        return level.isClientSide() ?
                createTickerHelper(blockEntityType, MannequinsBlockEntities.MANNEQUIN.get(), MannequinBlockEntity::clientTick) :
                createTickerHelper(blockEntityType, MannequinsBlockEntities.MANNEQUIN.get(), MannequinBlockEntity::tick);
    }
}
