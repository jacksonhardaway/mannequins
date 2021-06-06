package me.jaackson.mannequins.common.network;

import me.jaackson.mannequins.Mannequins;
import me.jaackson.mannequins.common.network.handler.MannequinsClientNetHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public class ClientboundOpenMannequinScreen implements MannequinsPacket
{
    public static final ResourceLocation CHANNEL = new ResourceLocation(Mannequins.MOD_ID, "open_mannequin_screen");

    private final int containerId;
    private final int entityId;

    public ClientboundOpenMannequinScreen(int id, int entity)
    {
        this.containerId = id;
        this.entityId = entity;
    }

    public FriendlyByteBuf write(FriendlyByteBuf buf)
    {
        buf.writeByte(this.containerId);
        buf.writeInt(this.entityId);
        return buf;
    }

    public static ClientboundOpenMannequinScreen read(FriendlyByteBuf buf)
    {
        return new ClientboundOpenMannequinScreen(buf.readUnsignedByte(), buf.readInt());
    }

    public static void handle(ClientboundOpenMannequinScreen packet)
    {
         MannequinsClientNetHandler.handleOpenMannequinScreen(packet);
    }

    public int getContainerId()
    {
        return this.containerId;
    }

    public int getEntityId()
    {
        return this.entityId;
    }
}