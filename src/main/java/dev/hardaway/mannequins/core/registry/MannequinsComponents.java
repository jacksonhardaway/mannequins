package dev.hardaway.mannequins.core.registry;

import dev.hardaway.mannequins.api.MannequinExpression;
import dev.hardaway.mannequins.api.MannequinPose;
import dev.hardaway.mannequins.core.Mannequins;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class MannequinsComponents {

    public static final DeferredRegister.DataComponents REGISTRY = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, Mannequins.MOD_ID);

    public static final Supplier<DataComponentType<MannequinPose>> MANNEQUIN_POSE = REGISTRY.registerComponentType("mannequin_pose", builder ->
            builder.persistent(MannequinPose.CODEC).networkSynchronized(MannequinPose.STREAM_CODEC).cacheEncoding()
    );

    public static final Supplier<DataComponentType<Holder<MannequinExpression>>> MANNEQUIN_EXPRESSION = REGISTRY.registerComponentType("mannequin_expression", builder ->
            builder.persistent(MannequinExpression.CODEC).networkSynchronized(MannequinExpression.STREAM_CODEC).cacheEncoding()
    );
}
