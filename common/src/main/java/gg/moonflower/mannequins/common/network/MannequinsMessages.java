package gg.moonflower.mannequins.common.network;

import gg.moonflower.mannequins.common.network.play.ClientboundAttackMannequin;
import gg.moonflower.mannequins.common.network.play.ClientboundOpenMannequinScreen;
import gg.moonflower.mannequins.common.network.play.ServerboundSetMannequinPose;
import gg.moonflower.mannequins.common.network.play.handler.MannequinsClientPlayPacketHandlerImpl;
import gg.moonflower.mannequins.common.network.play.handler.MannequinsServerPlayPacketHandlerImpl;
import gg.moonflower.mannequins.core.Mannequins;
import gg.moonflower.pollen.api.network.PollinatedPlayNetworkChannel;
import gg.moonflower.pollen.api.network.packet.PollinatedPacketDirection;
import gg.moonflower.pollen.api.registry.NetworkRegistry;
import net.minecraft.resources.ResourceLocation;

public class MannequinsMessages {

    public static final PollinatedPlayNetworkChannel PLAY = NetworkRegistry.createPlay(new ResourceLocation(Mannequins.MOD_ID, "play"), "1", () -> new MannequinsClientPlayPacketHandlerImpl(), () -> new MannequinsServerPlayPacketHandlerImpl());

    public static void init() {
        PLAY.register(ClientboundAttackMannequin.class, ClientboundAttackMannequin::new, PollinatedPacketDirection.PLAY_CLIENTBOUND);
        PLAY.register(ClientboundOpenMannequinScreen.class, ClientboundOpenMannequinScreen::new, PollinatedPacketDirection.PLAY_CLIENTBOUND);
        PLAY.register(ServerboundSetMannequinPose.class, ServerboundSetMannequinPose::new, PollinatedPacketDirection.PLAY_SERVERBOUND);
    }
}
