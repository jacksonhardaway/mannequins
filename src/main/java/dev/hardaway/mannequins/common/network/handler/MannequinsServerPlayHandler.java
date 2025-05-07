package dev.hardaway.mannequins.common.network.handler;

import dev.hardaway.mannequins.common.menu.MannequinMenu;
import dev.hardaway.mannequins.common.network.payload.ServerboundSetMannequinPosePayload;
import dev.hardaway.mannequins.core.Mannequins;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class MannequinsServerPlayHandler {
    private static final Component INVALID_POSE_DISCONNECT = Component.translatable("disconnect." + Mannequins.MOD_ID + ".invalid_mannequin_pose");

    public static void handleSyncMannequinPose(ServerboundSetMannequinPosePayload payload, IPayloadContext ctx) {
        Player player = ctx.player();
        if (player.containerMenu == null || player.containerMenu.containerId != payload.containerId())
            return;

        if (!(player.containerMenu instanceof MannequinMenu) || !player.containerMenu.stillValid(player)) {
            ctx.disconnect(INVALID_POSE_DISCONNECT);
            return;
        }

        ((MannequinMenu) player.containerMenu).getMannequin().setPose(payload.pose());
    }
}
