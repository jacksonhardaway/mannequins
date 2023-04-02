package gg.moonflower.mannequins.common.network.play;

import gg.moonflower.mannequins.common.network.play.handler.MannequinsClientPlayPacketHandler;
import gg.moonflower.pollen.api.network.v1.packet.PollinatedPacket;
import gg.moonflower.pollen.api.network.v1.packet.PollinatedPacketContext;
import net.minecraft.network.FriendlyByteBuf;

/**
 * @author Ocelot
 */
public class ClientboundOpenMannequinScreen implements PollinatedPacket<MannequinsClientPlayPacketHandler> {
    private final int containerId;
    private final int entityId;

    public ClientboundOpenMannequinScreen(int id, int entity) {
        this.containerId = id;
        this.entityId = entity;
    }

    public ClientboundOpenMannequinScreen(FriendlyByteBuf buf) {
        this.containerId = buf.readUnsignedByte();
        this.entityId = buf.readInt();
    }

    @Override
    public void writePacketData(FriendlyByteBuf buf) {
        buf.writeByte(this.containerId);
        buf.writeInt(this.entityId);
    }

    @Override
    public void processPacket(MannequinsClientPlayPacketHandler handler, PollinatedPacketContext ctx) {
        handler.handleOpenMannequinScreen(this, ctx);
    }

    public int getContainerId() {
        return this.containerId;
    }

    public int getEntityId() {
        return this.entityId;
    }
}