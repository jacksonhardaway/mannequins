package dev.hardaway.mannequins.common.block.entity;

import com.mojang.datafixers.util.Pair;
import com.mojang.logging.LogUtils;
import dev.hardaway.mannequins.api.MannequinExpression;
import dev.hardaway.mannequins.api.MannequinPose;
import dev.hardaway.mannequins.common.compat.vanity.MannequinsVanityCompat;
import dev.hardaway.mannequins.common.entity.Dummy;
import dev.hardaway.mannequins.common.menu.MannequinInventory;
import dev.hardaway.mannequins.common.menu.MannequinMenu;
import dev.hardaway.mannequins.core.registry.MannequinsBlockEntities;
import dev.hardaway.mannequins.core.registry.MannequinsComponents;
import dev.hardaway.mannequins.core.registry.MannequinsRegistries;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Rotations;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.RegistryFixedCodec;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.Nameable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import tech.thatgravyboat.vanity.common.registries.ModDataComponents;

import java.util.Objects;

public class MannequinBlockEntity extends BlockEntity implements MenuProvider, Nameable {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final Rotations DEFAULT_HEAD_POSE = new Rotations(0.0F, 0.0F, 0.0F);
    private static final Rotations DEFAULT_BODY_POSE = new Rotations(0.0F, 0.0F, 0.0F);
    private static final Rotations DEFAULT_LEFT_ARM_POSE = new Rotations(-10.0F, 0.0F, -10.0F);
    private static final Rotations DEFAULT_RIGHT_ARM_POSE = new Rotations(-15.0F, 0.0F, 10.0F);
    public static final MannequinPose DEFAULT_POSE = new MannequinPose(DEFAULT_HEAD_POSE, DEFAULT_BODY_POSE, DEFAULT_LEFT_ARM_POSE, DEFAULT_RIGHT_ARM_POSE);

    private final MannequinInventory inventory = new MannequinInventory();

    private MannequinPose pose = DEFAULT_POSE;
    private @Nullable Pair<ResourceLocation, String> vanity;
    private @Nullable Holder<MannequinExpression> expression;
    private @Nullable Dummy dummy;
    private @Nullable Component name;

    public MannequinBlockEntity(BlockPos pos, BlockState blockState) {
        super(MannequinsBlockEntities.MANNEQUIN.get(), pos, blockState);
    }

    @Override
    public void setLevel(Level level) {
        super.setLevel(level);
        if (level.isClientSide()) this.dummy = new Dummy(level, this);
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        if (this.level.isClientSide()) this.dummy.setRemoved(Entity.RemovalReason.DISCARDED);
    }

    @Override
    public void writeClientSideData(AbstractContainerMenu menu, RegistryFriendlyByteBuf buffer) {
        buffer.writeBlockPos(this.getBlockPos());
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return this.saveCustomOnly(registries);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        if (tag.contains("CustomName", 8)) {
            this.name = parseCustomNameSafe(tag.getString("CustomName"), registries);
        }

        MannequinPose.CODEC.parse(NbtOps.INSTANCE, tag.getCompound("mannequin_pose"))
                .resultOrPartial(LOGGER::error)
                .ifPresent(this::setPose);

        if (tag.contains("mannequin_expression", CompoundTag.TAG_STRING)) {
            RegistryFixedCodec.create(MannequinsRegistries.MANNEQUIN_EXPRESSIONS).parse(RegistryOps.create(NbtOps.INSTANCE, registries), tag.get("mannequin_expression"))
                    .resultOrPartial(LOGGER::error)
                    .ifPresent(this::setExpression);
        }

        this.inventory.deserializeNBT(registries, tag.getCompound("inventory"));

        if (MannequinsVanityCompat.isActive() && tag.contains("vanity", CompoundTag.TAG_COMPOUND)) {
            Objects.requireNonNull(ModDataComponents.STYLE.get().codec())
                    .parse(NbtOps.INSTANCE, tag.getCompound("vanity"))
                    .resultOrPartial(LOGGER::error)
                    .ifPresent(vanity -> this.vanity = vanity);
        }

        super.loadAdditional(tag, registries);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        if (this.name != null) {
            tag.putString("CustomName", Component.Serializer.toJson(this.name, registries));
        }

        MannequinPose.CODEC
                .encodeStart(NbtOps.INSTANCE, this.pose)
                .resultOrPartial(LOGGER::error)
                .ifPresent(poseTag -> tag.put("mannequin_pose", poseTag));

        if (this.expression != null) {
            RegistryFixedCodec.create(MannequinsRegistries.MANNEQUIN_EXPRESSIONS)
                    .encodeStart(RegistryOps.create(NbtOps.INSTANCE, registries), this.expression)
                    .resultOrPartial(LOGGER::error)
                    .ifPresent(expressionTag -> tag.put("mannequin_expression", expressionTag));
        }

        tag.put("inventory", this.inventory.serializeNBT(registries));

        if (MannequinsVanityCompat.isActive() && this.vanity != null) {
            Objects.requireNonNull(ModDataComponents.STYLE.get().codec())
                    .encodeStart(NbtOps.INSTANCE, this.vanity)
                    .resultOrPartial(LOGGER::error)
                    .ifPresent(vanityTag -> tag.put("vanity", vanityTag));
        }

        super.saveAdditional(tag, registries);
    }

    @Override
    protected void applyImplicitComponents(BlockEntity.DataComponentInput componentInput) {
        this.name = componentInput.get(DataComponents.CUSTOM_NAME);
        this.pose = componentInput.getOrDefault(MannequinsComponents.MANNEQUIN_POSE, DEFAULT_POSE);
        this.expression = componentInput.get(MannequinsComponents.MANNEQUIN_EXPRESSION);

        if (MannequinsVanityCompat.isActive()) {
            this.vanity = componentInput.get(ModDataComponents.STYLE);
        }
    }

    @Override
    protected void collectImplicitComponents(DataComponentMap.Builder components) {
        components.set(DataComponents.CUSTOM_NAME, this.name);
        components.set(MannequinsComponents.MANNEQUIN_POSE, this.pose);
        components.set(MannequinsComponents.MANNEQUIN_EXPRESSION, this.expression);
        if (MannequinsVanityCompat.isActive()) {
            components.set(ModDataComponents.STYLE, this.vanity);
        }
    }

    @Override
    public void removeComponentsFromTag(CompoundTag tag) {
        tag.remove("CustomName");
        tag.remove("mannequin_pose");
        tag.remove("mannequin_expression");
        tag.remove("vanity");
    }

    public void markDirty() {
        BlockPos pos = this.getBlockPos();
        BlockState state = this.getBlockState();

        if (this.level != null) {
            this.setChanged();
            this.level.sendBlockUpdated(pos, state, state, 3);
            this.level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(state));
        }
    }

    public @Nullable Pair<ResourceLocation, String> getVanity() {
        return vanity;
    }

    public Dummy getDummy() {
        return dummy;
    }

    public @Nullable Holder<MannequinExpression> getExpression() {
        return expression;
    }

    public void setExpression(@Nullable Holder<MannequinExpression> expression) {
        this.expression = expression;
        this.markDirty();
    }

    public MannequinPose getPose() {
        return pose;
    }

    public void setPose(MannequinPose pose) {
        this.pose = pose;
        this.markDirty();
    }

    public MannequinInventory getInventory() {
        return inventory;
    }

    @Override
    public Component getName() {
        return this.name != null ? this.name : this.getBlockState().getBlock().getName();
    }

    @Override
    public Component getDisplayName() {
        return this.getName();
    }

    @Nullable
    @Override
    public Component getCustomName() {
        return this.name;
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
        return new MannequinMenu(containerId, playerInventory, ContainerLevelAccess.create(this.getLevel(), this.getBlockPos()), this);
    }
}
