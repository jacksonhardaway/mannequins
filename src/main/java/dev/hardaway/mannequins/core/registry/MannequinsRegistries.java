package dev.hardaway.mannequins.core.registry;

import dev.hardaway.mannequins.api.DummyExpression;
import dev.hardaway.mannequins.core.Mannequins;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;

public class MannequinsRegistries {
    public static final ResourceKey<Registry<DummyExpression>> EXPRESSIONS = ResourceKey.createRegistryKey(Mannequins.path("expressions"));

    @SubscribeEvent
    public static void registerDatapackRegistries(DataPackRegistryEvent.NewRegistry event) {
        event.dataPackRegistry(
                MannequinsRegistries.EXPRESSIONS,
                DummyExpression.DIRECT_CODEC,
                DummyExpression.DIRECT_CODEC
        );
    }
}
