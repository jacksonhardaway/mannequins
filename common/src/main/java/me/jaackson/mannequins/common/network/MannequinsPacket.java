package me.jaackson.mannequins.common.network;

import net.minecraft.network.FriendlyByteBuf;

/**
 * @author Jackson
 */
public interface MannequinsPacket {
    FriendlyByteBuf write(FriendlyByteBuf buf);
}
