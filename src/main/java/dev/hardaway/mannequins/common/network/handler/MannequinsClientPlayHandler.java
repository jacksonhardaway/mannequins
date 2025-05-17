package dev.hardaway.mannequins.common.network.handler;

import dev.hardaway.mannequins.common.block.MannequinBlock;
import dev.hardaway.mannequins.common.block.entity.MannequinBlockEntity;
import dev.hardaway.mannequins.common.network.payload.ClientboundAttackMannequinPayload;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class MannequinsClientPlayHandler {

    public static void handleMannequinAttack(ClientboundAttackMannequinPayload payload, IPayloadContext ctx) {
        Player player = ctx.player();
        Level level = player.level();
        BlockPos pos = payload.pos();
        BlockState state = level.getBlockState(pos);

        if (!(state.getBlock() instanceof MannequinBlock mannequin))
            return;
        // TODO: add arrows to mannequin

        // TODO: clean
        MannequinBlockEntity mannequinEntity = (MannequinBlockEntity) mannequin.getMannequin(level, state, pos);
        if (mannequinEntity != null)
            mannequinEntity.attack(payload.attackYaw());
    }
}
