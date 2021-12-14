package gg.moonflower.mannequins.common.network.play.handler;

import gg.moonflower.pollen.api.network.packet.PollinatedPacketContext;
import gg.moonflower.mannequins.common.network.play.ServerboundSetMannequinPose;

public interface MannequinsServerPlayPacketHandler {

    void handleSetMannequinPose(ServerboundSetMannequinPose pkt, PollinatedPacketContext ctx);
}
