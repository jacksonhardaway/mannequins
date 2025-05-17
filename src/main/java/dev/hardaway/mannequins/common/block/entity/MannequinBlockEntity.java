package dev.hardaway.mannequins.common.block.entity;

import dev.hardaway.mannequins.api.DummyExpression;
import dev.hardaway.mannequins.common.block.DummyBlock;
import dev.hardaway.mannequins.core.registry.MannequinsBlockEntities;
import dev.hardaway.mannequins.core.registry.MannequinsExpressions;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.RotationSegment;

import java.util.Optional;

public class MannequinBlockEntity extends DummyBlockEntity {

    private float attackAnimationXFactor;
    private float attackAnimationZFactor;
    private int attackAnimation;

    public MannequinBlockEntity(BlockPos pos, BlockState blockState) {
        super(MannequinsBlockEntities.MANNEQUIN.get(), pos, blockState);
    }

    public static void clientTick(Level level, BlockPos pos, BlockState state, MannequinBlockEntity dummy) {
        if (dummy.attackAnimation <= 0)
            return;
        dummy.attackAnimation--;
    }

    @Override
    public void randomizePose() {
        Level level = this.getLevel();
        if (!(this.getExpression() != null && this.getExpression().is(MannequinsExpressions.MANNEQUIN_TROLLED)) && level.getRandom().nextDouble() < 0.01) {
            Optional<Holder.Reference<DummyExpression>> trolder = level.registryAccess().holder(MannequinsExpressions.MANNEQUIN_TROLLED);
            if (trolder.isPresent()) {
                this.setExpression(trolder.get());
                return;
            }
        }

        super.randomizePose();
    }

    public void attack(float attackYaw) {
        float bodyRot = -RotationSegment.convertToDegrees(this.getBlockState().getValue(DummyBlock.ROTATION));
        float rotation = attackYaw + bodyRot * Mth.DEG_TO_RAD;
        this.attackAnimation = 40;
        this.attackAnimationXFactor = Mth.cos(rotation);
        this.attackAnimationZFactor = Mth.sin(rotation);
    }

    // Graph: https://www.desmos.com/calculator/xkhzglfwkm
    private float getAttackAnimation(float partialTicks) {
        float x = this.attackAnimation - partialTicks;
        return Mth.cos(x) / 2F * Mth.sqrt(x) / (50 - x);
    }

    public boolean hasAnimation() {
        return this.attackAnimation > 0;
    }

    public float getAnimationRotationX(float partialTicks) {
        return this.attackAnimationXFactor * this.getAttackAnimation(partialTicks);
    }

    public float getAnimationRotationZ(float partialTicks) {
        return this.attackAnimationZFactor * this.getAttackAnimation(partialTicks);
    }
}
