package dev.hardaway.mannequins.common.block.entity;

import dev.hardaway.mannequins.api.DummyExpression;
import dev.hardaway.mannequins.core.registry.MannequinsBlockEntities;
import dev.hardaway.mannequins.core.registry.MannequinsExpressions;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Optional;

public class StatueBlockEntity extends DummyBlockEntity {

    public StatueBlockEntity(BlockPos pos, BlockState blockState) {
        super(MannequinsBlockEntities.STATUE.get(), pos, blockState);
    }

    @Override
    public void randomizePose() {
        Level level = this.getLevel();
        if (!(this.getExpression() != null && this.getExpression().is(MannequinsExpressions.STATUE_TROLLED)) && level.getRandom().nextDouble() < 0.01) {
            Optional<Holder.Reference<DummyExpression>> trolder = level.registryAccess().holder(MannequinsExpressions.STATUE_TROLLED);
            if (trolder.isPresent()) {
                this.setExpression(trolder.get());
                return;
            }
        }

        super.randomizePose();
    }
}
