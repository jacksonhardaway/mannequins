package dev.hardaway.mannequins.core.util;

import dev.hardaway.mannequins.api.DummyPose;
import net.minecraft.core.Rotations;

// TODO: make this not create a bajillion editor poses
public enum BuiltinPoses {
    NONE(new DummyPose()),

    DEFAULT(new DummyPose(
            RotationUtil.ZERO,
            RotationUtil.ZERO,
            new Rotations(-10.0F, 0.0F, -10.0F),
            new Rotations(-15.0F, 0.0F, 10.0F)
    )),

    SOLEMN(new DummyPose(
            new Rotations(15.0F, 0.0F, 0.0F),
            new Rotations(0.0F, 0.0F, 2.0F),
            new Rotations(-30.0F, 15.0F, 15.0F),
            new Rotations(-60.0F, -20.0F, -10.0F)
    )),

    ATHENA(new DummyPose(
            new Rotations(-5.0F, 0.0F, 0.0F),
            new Rotations(0.0F, 0.0F, 2.0F),
            new Rotations(10.0F, 0.0F, -5.0F),
            new Rotations(-60.0F, 20.0F, -10.0F)
    )),

    BRANDISH(new DummyPose(
            new Rotations(-15.0F, 0.0F, 0.0F),
            new Rotations(0.0F, 0.0F, -2.0F),
            new Rotations(20.0F, 0.0F, -10.0F),
            new Rotations(-110.0F, 50.0F, 0.0F)
    )),

    HONOR(new DummyPose(
            new Rotations(-15.0F, 0.0F, 0.0F),
            RotationUtil.ZERO,
            new Rotations(-110.0F, 35.0F, 0.0F),
            new Rotations(-110.0F, -35.0F, 0.0F)
    )),

    ENTERTAIN(new DummyPose(
            new Rotations(-15.0F, 0.0F, 0.0F),
            RotationUtil.ZERO,
            new Rotations(-110.0F, -35.0F, 0.0F),
            new Rotations(-110.0F, 35.0F, 0.0F)
    )),

    SALUTE(new DummyPose(
            RotationUtil.ZERO,
            RotationUtil.ZERO,
            new Rotations(10.0F, 0.0F, -5.0F),
            new Rotations(-70.0F, 0.0F, -40.0F)
    )),

    RIPOSTE(new DummyPose(
            new Rotations(16.0F, -20.0F, 0.0F),
            RotationUtil.ZERO,
            new Rotations(4.0F, -8.0F, 237.0F),
            new Rotations(246.0F, 0.0F, 89.0F)
    )),

    ZOMBIE(new DummyPose(
            new Rotations(-10.0F, 0.0F, -5.0F),
            RotationUtil.ZERO,
            new Rotations(-105.0F, 0.0F, 0.0F),
            new Rotations(-100.0F, 0.0F, 0.0F)
    )),

    CANCAN_A(new DummyPose(
            new Rotations(0.0F, 22.0F, 0.0F),
            new Rotations(-5.0F, 18.0F, 0.0F),
            new Rotations(8.0F, 0.0F, -114.0F),
            new Rotations(0.0F, 84.0F, 111.0F)
    )),

    CANCAN_B(new DummyPose(
            new Rotations(0.0F, -18.0F, 0.0F),
            new Rotations(-10.0F, -20.0F, 0.0F),
            new Rotations(0.0F, 0.0F, -112.0F),
            new Rotations(8.0F, 90.0F, 111.0F)
    )),

    HERO(new DummyPose(
            new Rotations(0.0F, 8.0F, 0.0F),
            new Rotations(-4.0F, 67.0F, 0.0F),
            new Rotations(16.0F, 32.0F, -8.0F),
            new Rotations(-99.0F, 63.0F, 0.0F)
    ));

    public static final BuiltinPoses[] VALUES = BuiltinPoses.values();

    private final DummyPose pose;

    BuiltinPoses(DummyPose pose) {
        this.pose = pose;
    }

    public DummyPose getPose() {
        return pose;
    }
}