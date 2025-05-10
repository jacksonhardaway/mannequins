package dev.hardaway.mannequins.common.network.handler;

import dev.hardaway.mannequins.common.menu.DummyEditorMenu;
import dev.hardaway.mannequins.common.network.payload.ServerboundMannequinActionPayload;
import dev.hardaway.mannequins.common.network.payload.ServerboundSetMannequinPosePayload;
import dev.hardaway.mannequins.core.Mannequins;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public class MannequinsServerPlayHandler {
    private static final Component INVALID_POSE_DISCONNECT = Component.translatable("disconnect." + Mannequins.MOD_ID + ".invalid_mannequin_pose");

    public static void handleSyncMannequinPose(ServerboundSetMannequinPosePayload payload, IPayloadContext ctx) {
        Player player = ctx.player();
        if (!(player.containerMenu instanceof DummyEditorMenu menu) || player.containerMenu.containerId != payload.containerId() || !player.containerMenu.stillValid(player))
            return;

        menu.getDummy().setPose(payload.pose());
    }

    public static void handleMannequinAction(ServerboundMannequinActionPayload payload, IPayloadContext ctx) {
        Player player = ctx.player();
        if (!(player.containerMenu instanceof DummyEditorMenu menu) || player.containerMenu.containerId != payload.containerId() || !player.containerMenu.stillValid(player))
            return;

        switch (payload.action()) {
            case RANDOMIZE -> menu.getDummy().randomizePose();
            case RESET -> menu.getDummy().resetPose();
        }
    }
}
