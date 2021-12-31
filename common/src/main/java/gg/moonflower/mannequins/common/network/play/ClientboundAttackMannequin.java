package gg.moonflower.mannequins.common.network.play;

import gg.moonflower.mannequins.common.network.play.handler.MannequinsClientPlayPacketHandler;
import gg.moonflower.pollen.api.network.packet.PollinatedPacket;
import gg.moonflower.pollen.api.network.packet.PollinatedPacketContext;
import net.minecraft.network.FriendlyByteBuf;

/**
 * @author Ocelot
 */
public class ClientboundAttackMannequin implements PollinatedPacket<MannequinsClientPlayPacketHandler> {
    private final int entityId;
    private final float attackYaw;

    public ClientboundAttackMannequin(int entityId, float attackYaw) {
        this.entityId = entityId;
        this.attackYaw = attackYaw;
    }

    public ClientboundAttackMannequin(FriendlyByteBuf buf) {
        this.entityId = buf.readVarInt();
        this.attackYaw = buf.readFloat();
    }

    @Override
    public void writePacketData(FriendlyByteBuf buf) {
        buf.writeVarInt(this.entityId);
        buf.writeFloat(this.attackYaw);
    }

    @Override
    public void processPacket(MannequinsClientPlayPacketHandler handler, PollinatedPacketContext ctx) {
        handler.handleAttackMannequin(this, ctx);
    }

    public int getEntityId() {
        return this.entityId;
    }

    public float getAttackYaw() {
        return attackYaw;
    }
}