package gg.moonflower.mannequins.common.network.play.handler;

import gg.moonflower.pollen.api.network.packet.PollinatedPacketContext;
import gg.moonflower.mannequins.common.network.play.ClientboundAttackMannequin;
import gg.moonflower.mannequins.common.network.play.ClientboundOpenMannequinScreen;

public interface MannequinsClientPlayPacketHandler {

    void handleOpenMannequinScreen(ClientboundOpenMannequinScreen pkt, PollinatedPacketContext ctx);

    void handleAttackMannequin(ClientboundAttackMannequin pkt, PollinatedPacketContext ctx);
}
