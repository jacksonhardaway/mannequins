package me.jaackson.mannequins.common.network;

import me.jaackson.mannequins.Mannequins;
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

    public ClientboundAttackMannequin(FriendlyByteBuf buf) {
        this.entityId = buf.readVarInt();
        this.attackYaw = buf.readFloat();
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeVarInt(this.entityId);
        buf.writeFloat(this.attackYaw);
    }

    @Override
    public ResourceLocation getChannel() {
        return CHANNEL;
    }

    public int getEntityId() {
        return this.entityId;
    }

    public float getAttackYaw() {
        return attackYaw;
    }
}