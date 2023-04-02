package gg.moonflower.mannequins.common.network.play.handler;

import gg.moonflower.mannequins.common.network.play.ServerboundSetMannequinPose;
import gg.moonflower.pollen.api.network.v1.packet.PollinatedPacketContext;

public interface MannequinsServerPlayPacketHandler {

    void handleSetMannequinPose(ServerboundSetMannequinPose pkt, PollinatedPacketContext ctx);
}
