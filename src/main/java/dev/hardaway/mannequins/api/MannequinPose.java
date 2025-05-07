package dev.hardaway.mannequins.api;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.hardaway.mannequins.core.util.RotationUtil;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.Rotations;
import net.minecraft.network.codec.StreamCodec;

public record MannequinPose(Rotations head, Rotations body, Rotations leftArm, Rotations rightArm) {

    public static final Codec<MannequinPose> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            RotationUtil.CODEC.optionalFieldOf("head", RotationUtil.ZERO).forGetter(MannequinPose::head),
            RotationUtil.CODEC.optionalFieldOf("body", RotationUtil.ZERO).forGetter(MannequinPose::body),
            RotationUtil.CODEC.optionalFieldOf("left_arm", RotationUtil.ZERO).forGetter(MannequinPose::leftArm),
            RotationUtil.CODEC.optionalFieldOf("right_arm", RotationUtil.ZERO).forGetter(MannequinPose::rightArm)
    ).apply(instance, MannequinPose::new));

    public static final StreamCodec<ByteBuf, MannequinPose> STREAM_CODEC = StreamCodec.composite(
            Rotations.STREAM_CODEC,
            MannequinPose::head,
            Rotations.STREAM_CODEC,
            MannequinPose::body,
            Rotations.STREAM_CODEC,
            MannequinPose::leftArm,
            Rotations.STREAM_CODEC,
            MannequinPose::rightArm,
            MannequinPose::new
    );

    public MannequinPose withHeadPose(Rotations rotation) {
        return new MannequinPose(rotation, this.body, this.leftArm, this.rightArm);
    }

    public MannequinPose withBodyPose(Rotations rotation) {
        return new MannequinPose(this.head, rotation, this.leftArm, this.rightArm);
    }

    public MannequinPose withLeftArmPose(Rotations rotation) {
        return new MannequinPose(this.head, this.body, rotation, this.rightArm);
    }

    public MannequinPose withRightAmPose(Rotations rotation) {
        return new MannequinPose(this.head, this.body, this.leftArm, rotation);
    }
}
