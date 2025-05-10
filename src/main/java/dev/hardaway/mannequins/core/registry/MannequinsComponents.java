package dev.hardaway.mannequins.core.registry;

import dev.hardaway.mannequins.api.DummyExpression;
import dev.hardaway.mannequins.api.DummyPose;
import dev.hardaway.mannequins.core.Mannequins;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class MannequinsComponents {

    public static final DeferredRegister.DataComponents REGISTRY = DeferredRegister.createDataComponents(Registries.DATA_COMPONENT_TYPE, Mannequins.MOD_ID);

    public static final Supplier<DataComponentType<DummyPose>> POSE = REGISTRY.registerComponentType("pose", builder ->
            builder.persistent(DummyPose.CODEC).networkSynchronized(DummyPose.STREAM_CODEC).cacheEncoding()
    );

    public static final Supplier<DataComponentType<Holder<DummyExpression>>> EXPRESSION = REGISTRY.registerComponentType("expression", builder ->
            builder.persistent(DummyExpression.CODEC).networkSynchronized(DummyExpression.STREAM_CODEC).cacheEncoding()
    );
}
