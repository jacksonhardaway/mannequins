package dev.hardaway.mannequins.common.network.payload;

import dev.hardaway.mannequins.core.Mannequins;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record ClientboundAttackMannequinPayload(BlockPos pos, float attackYaw) implements CustomPacketPayload {

    public static final Type<ClientboundAttackMannequinPayload> PACKET_TYPE = new Type<>(Mannequins.path("attack_mannequin"));

    public static final StreamCodec<FriendlyByteBuf, ClientboundAttackMannequinPayload> CODEC = StreamCodec.composite(
            BlockPos.STREAM_CODEC,
            ClientboundAttackMannequinPayload::pos,
            ByteBufCodecs.FLOAT,
            ClientboundAttackMannequinPayload::attackYaw,
            ClientboundAttackMannequinPayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return PACKET_TYPE;
    }
}
