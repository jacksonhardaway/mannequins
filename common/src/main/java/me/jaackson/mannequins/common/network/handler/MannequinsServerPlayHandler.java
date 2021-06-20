package me.jaackson.mannequins.common.network.handler;

import me.jaackson.mannequins.Mannequins;
import me.jaackson.mannequins.bridge.PlayerBridge;
import me.jaackson.mannequins.common.menu.MannequinInventoryMenu;
import me.jaackson.mannequins.common.network.ServerboundSetMannequinPose;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

public class MannequinsServerPlayHandler {
    private static final Component INVALID_POSE_DISCONNECT = new TranslatableComponent("disconnect." + Mannequins.MOD_ID + ".invalid_mannequin_pose");

    public static void handleSetMannequinPose(ServerboundSetMannequinPose pkt, Player player) {
        if (!(player instanceof ServerPlayer))
            return;

        ServerPlayer serverPlayer = (ServerPlayer) player;
        if (PlayerBridge.getContainerId(serverPlayer) != pkt.getContainerId())
            return;

        if (!(serverPlayer.containerMenu instanceof MannequinInventoryMenu) || !serverPlayer.containerMenu.stillValid(serverPlayer)) {
            serverPlayer.connection.disconnect(INVALID_POSE_DISCONNECT);
            return;
        }

        ((MannequinInventoryMenu) serverPlayer.containerMenu).setMannequinPose(pkt.getHeadRotations(), pkt.getBodyRotations(), pkt.getLeftArmRotations(), pkt.getRightArmRotations());
    }
}
