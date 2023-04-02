package gg.moonflower.mannequins.common.network.play.handler;

import gg.moonflower.mannequins.common.menu.MannequinInventoryMenu;
import gg.moonflower.mannequins.common.network.play.ServerboundSetMannequinPose;
import gg.moonflower.mannequins.core.Mannequins;
import gg.moonflower.pollen.api.network.v1.packet.PollinatedPacketContext;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class MannequinsServerPlayPacketHandlerImpl implements MannequinsServerPlayPacketHandler {
    private static final Component INVALID_POSE_DISCONNECT = Component.translatable("disconnect." + Mannequins.MOD_ID + ".invalid_mannequin_pose");

    @Override
    public void handleSetMannequinPose(ServerboundSetMannequinPose pkt, PollinatedPacketContext ctx) {
        ServerPlayer player = ctx.getSender();
        if (player == null)
            return;

        if (player.containerMenu == null || player.containerMenu.containerId != pkt.getContainerId())
            return;

        if (!(player.containerMenu instanceof MannequinInventoryMenu) || !player.containerMenu.stillValid(player)) {
            player.connection.disconnect(INVALID_POSE_DISCONNECT);
            return;
        }

        ((MannequinInventoryMenu) player.containerMenu).setMannequinPose(pkt.getHeadRotations(), pkt.getBodyRotations(), pkt.getLeftArmRotations(), pkt.getRightArmRotations());
    }
}
