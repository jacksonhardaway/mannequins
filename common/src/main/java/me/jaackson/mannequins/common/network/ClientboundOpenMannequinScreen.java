package me.jaackson.mannequins.common.network;

import me.jaackson.mannequins.Mannequins;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

/**
 * @author Ocelot
 */
public class ClientboundOpenMannequinScreen implements MannequinsPacket {
    public static final ResourceLocation CHANNEL = new ResourceLocation(Mannequins.MOD_ID, "open_mannequin_screen");

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
    public void write(FriendlyByteBuf buf) {
        buf.writeByte(this.containerId);
        buf.writeInt(this.entityId);
    }

    @Override
    public ResourceLocation getChannel() {
        return CHANNEL;
    }

    public int getContainerId() {
        return this.containerId;
    }

    public int getEntityId() {
        return this.entityId;
    }
}