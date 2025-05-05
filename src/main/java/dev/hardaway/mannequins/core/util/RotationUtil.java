package dev.hardaway.mannequins.core.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import net.minecraft.core.Rotations;

import java.util.List;

public class RotationUtil {
    public static final Rotations ZERO = new Rotations(0, 0, 0);

    public static final Codec<Rotations> CODEC = Codec.FLOAT
            .listOf(3, 3)
            .flatXmap(
                    floats ->
                            DataResult.success(new Rotations(floats.get(0), floats.get(1), floats.get(2))),
                    rotations ->
                            DataResult.success(List.of(rotations.getX(), rotations.getY(), rotations.getZ())));
}
