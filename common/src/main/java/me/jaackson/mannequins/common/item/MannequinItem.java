package me.jaackson.mannequins.common.item;

import me.jaackson.mannequins.MannequinsRegistry;
import me.jaackson.mannequins.common.entity.Mannequin;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public class MannequinItem extends Item {

    public MannequinItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Direction face = context.getClickedFace();
        if (face == Direction.DOWN)
            return InteractionResult.FAIL;

        Level level = context.getLevel();
        BlockPlaceContext blockContext = new BlockPlaceContext(context);
        BlockPos pos = blockContext.getClickedPos();
        ItemStack stack = context.getItemInHand();
        Vec3 center = Vec3.atBottomCenterOf(pos);
        AABB box = MannequinsRegistry.MANNEQUIN.get().getDimensions().makeBoundingBox(center.x(), center.y(), center.z());

        if (!level.noCollision(null, box, (entity) -> true) || !level.getEntities(null, box).isEmpty())
            return InteractionResult.FAIL;

        if (!level.isClientSide()) {
            ServerLevel serverLevel = (ServerLevel) level;

            float yRot = (float) Mth.floor((Mth.wrapDegrees(context.getRotation() - 180.0F) + 22.5F) / 45.0F) * 45.0F;
            Mannequin mannequin = MannequinsRegistry.MANNEQUIN.get().create(serverLevel, stack.getTag(), null, context.getPlayer(), pos, MobSpawnType.SPAWN_EGG, true, true);
            if (mannequin == null) {
                return InteractionResult.FAIL;
            }

            mannequin.moveTo(mannequin.getX(), mannequin.getY(), mannequin.getZ(), yRot, 0.0F);
            serverLevel.addFreshEntityWithPassengers(mannequin);
            level.playSound(null, mannequin.getX(), mannequin.getY(), mannequin.getZ(), MannequinsRegistry.ENTITY_MANNEQUIN_PLACE.get(), SoundSource.BLOCKS, 0.75F, 0.8F);
        }

        stack.shrink(1);
        return InteractionResult.sidedSuccess(level.isClientSide);
    }
}
