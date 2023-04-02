package gg.moonflower.mannequins.common.network;

import gg.moonflower.mannequins.common.network.play.ClientboundAttackMannequin;
import gg.moonflower.mannequins.common.network.play.ClientboundOpenMannequinScreen;
import gg.moonflower.mannequins.common.network.play.ServerboundSetMannequinPose;
import gg.moonflower.mannequins.core.Mannequins;
import gg.moonflower.pollen.api.network.v1.PollinatedPlayNetworkChannel;
import gg.moonflower.pollen.api.network.v1.packet.PollinatedPacketDirection;
import gg.moonflower.pollen.api.registry.network.v1.PollinatedNetworkRegistry;
import net.minecraft.resources.ResourceLocation;

public class MannequinsMessages {

    public static final PollinatedPlayNetworkChannel PLAY = PollinatedNetworkRegistry.createPlay(new ResourceLocation(Mannequins.MOD_ID, "play"), "1");

    public static void init() {
        PLAY.register(ClientboundAttackMannequin.class, ClientboundAttackMannequin::new, PollinatedPacketDirection.PLAY_CLIENTBOUND);
        PLAY.register(ClientboundOpenMannequinScreen.class, ClientboundOpenMannequinScreen::new, PollinatedPacketDirection.PLAY_CLIENTBOUND);
        PLAY.register(ServerboundSetMannequinPose.class, ServerboundSetMannequinPose::new, PollinatedPacketDirection.PLAY_SERVERBOUND);
    }
}
