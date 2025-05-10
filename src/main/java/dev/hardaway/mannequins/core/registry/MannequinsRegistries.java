package dev.hardaway.mannequins.core.registry;

import dev.hardaway.mannequins.api.DummyExpression;
import dev.hardaway.mannequins.core.Mannequins;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;

public class MannequinsRegistries {
    public static final ResourceKey<Registry<DummyExpression>> MANNEQUIN_EXPRESSIONS = ResourceKey.createRegistryKey(Mannequins.path("expressions"));

    @SubscribeEvent
    public static void registerDatapackRegistries(DataPackRegistryEvent.NewRegistry event) {
        event.dataPackRegistry(
                MannequinsRegistries.MANNEQUIN_EXPRESSIONS,
                DummyExpression.DIRECT_CODEC,
                DummyExpression.DIRECT_CODEC
        );
    }
}
