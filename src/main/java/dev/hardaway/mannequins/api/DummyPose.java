package dev.hardaway.mannequins.api;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.hardaway.mannequins.core.util.RotationUtil;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.Rotations;
import net.minecraft.network.codec.StreamCodec;

public record DummyPose(Rotations head, Rotations body, Rotations leftArm, Rotations rightArm) {

    public static final Codec<DummyPose> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            RotationUtil.CODEC.optionalFieldOf("head", RotationUtil.ZERO).forGetter(DummyPose::head),
            RotationUtil.CODEC.optionalFieldOf("body", RotationUtil.ZERO).forGetter(DummyPose::body),
            RotationUtil.CODEC.optionalFieldOf("left_arm", RotationUtil.ZERO).forGetter(DummyPose::leftArm),
            RotationUtil.CODEC.optionalFieldOf("right_arm", RotationUtil.ZERO).forGetter(DummyPose::rightArm)
    ).apply(instance, DummyPose::new));

    public static final StreamCodec<ByteBuf, DummyPose> STREAM_CODEC = StreamCodec.composite(
            Rotations.STREAM_CODEC,
            DummyPose::head,
            Rotations.STREAM_CODEC,
            DummyPose::body,
            Rotations.STREAM_CODEC,
            DummyPose::leftArm,
            Rotations.STREAM_CODEC,
            DummyPose::rightArm,
            DummyPose::new
    );

    public DummyPose() {
        this(RotationUtil.ZERO, RotationUtil.ZERO, RotationUtil.ZERO, RotationUtil.ZERO);
    }

    public DummyPose withHeadPose(Rotations rotation) {
        return new DummyPose(rotation, this.body, this.leftArm, this.rightArm);
    }

    public DummyPose withBodyPose(Rotations rotation) {
        return new DummyPose(this.head, rotation, this.leftArm, this.rightArm);
    }

    public DummyPose withLeftArmPose(Rotations rotation) {
        return new DummyPose(this.head, this.body, rotation, this.rightArm);
    }

    public DummyPose withRightAmPose(Rotations rotation) {
        return new DummyPose(this.head, this.body, this.leftArm, rotation);
    }
}
