package dev.hardaway.mannequins.core.registry;

import dev.hardaway.mannequins.common.component.MannequinPose;
import dev.hardaway.mannequins.core.Mannequins;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class MannequinsComponents {

    public static final DeferredRegister.DataComponents REGISTRY = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, Mannequins.MOD_ID);

    public static final Supplier<DataComponentType<MannequinPose>> MANNEQUIN_POSE = REGISTRY.registerComponentType("mannequin_pose", builder ->
            builder.persistent(MannequinPose.CODEC).cacheEncoding()
    );
}
