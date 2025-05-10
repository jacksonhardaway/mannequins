package dev.hardaway.mannequins.common.network.payload;

import dev.hardaway.mannequins.core.Mannequins;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.codec.NeoForgeStreamCodecs;

public record ServerboundMannequinActionPayload(int containerId, Action action) implements CustomPacketPayload {

    public static final Type<ServerboundMannequinActionPayload> PACKET_TYPE = new Type<>(Mannequins.path("mannequin_action"));

    public static final StreamCodec<FriendlyByteBuf, ServerboundMannequinActionPayload> CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT,
            ServerboundMannequinActionPayload::containerId,
            NeoForgeStreamCodecs.enumCodec(Action.class),
            ServerboundMannequinActionPayload::action,
            ServerboundMannequinActionPayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return PACKET_TYPE;
    }

    public enum Action {
        RANDOMIZE,
        RESET
    }
}
