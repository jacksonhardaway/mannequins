package me.jaackson.mannequins.common.network;

import net.minecraft.network.FriendlyByteBuf;

public interface MannequinsPacket {
    FriendlyByteBuf write(FriendlyByteBuf buf);
}
