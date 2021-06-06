package me.jaackson.mannequins.common.network;

import me.jaackson.mannequins.Mannequins;
import me.jaackson.mannequins.common.network.handler.MannequinsClientNetHandler;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

/**
 * @author Ocelot
 */
public class ClientboundAttackMannequin implements MannequinsPacket {
    public static final ResourceLocation CHANNEL = new ResourceLocation(Mannequins.MOD_ID, "attack_mannequin");

    private final int entityId;
    private final float attackYaw;

    public ClientboundAttackMannequin(int entityId, float attackYaw) {
        this.entityId = entityId;
        this.attackYaw = attackYaw;
    }

    public static ClientboundAttackMannequin read(FriendlyByteBuf buf) {
        return new ClientboundAttackMannequin(buf.readVarInt(), buf.readFloat());
    }

    public static void handle(ClientboundAttackMannequin packet) {
        MannequinsClientNetHandler.handleAttackMannequin(packet);
    }

    public FriendlyByteBuf write(FriendlyByteBuf buf) {
        buf.writeVarInt(this.entityId);
        buf.writeFloat(this.attackYaw);
        return buf;
    }

    public int getEntityId() {
        return this.entityId;
    }

    public float getAttackYaw() {
        return attackYaw;
    }
}