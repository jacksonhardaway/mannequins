package dev.hardaway.mannequins.common.network.payload;

import dev.hardaway.mannequins.api.MannequinPose;
import dev.hardaway.mannequins.core.Mannequins;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;

public record ServerboundSetMannequinPosePayload(int containerId, MannequinPose pose) implements CustomPacketPayload {

    public static final CustomPacketPayload.Type<ServerboundSetMannequinPosePayload> PACKET_TYPE = new CustomPacketPayload.Type<>(Mannequins.path("set_mannequin_pose"));

    public static final StreamCodec<ByteBuf, ServerboundSetMannequinPosePayload> CODEC = StreamCodec.composite(
            ByteBufCodecs.VAR_INT,
            ServerboundSetMannequinPosePayload::containerId,
            MannequinPose.STREAM_CODEC,
            ServerboundSetMannequinPosePayload::pose,
            ServerboundSetMannequinPosePayload::new
    );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return PACKET_TYPE;
    }
}
