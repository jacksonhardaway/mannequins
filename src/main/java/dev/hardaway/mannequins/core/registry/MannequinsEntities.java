package dev.hardaway.mannequins.core.registry;

import dev.hardaway.mannequins.common.entity.Dummy;
import dev.hardaway.mannequins.core.Mannequins;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class MannequinsEntities {

    public static final DeferredRegister<EntityType<?>> REGISTRY = DeferredRegister.create(Registries.ENTITY_TYPE, Mannequins.MOD_ID);

    public static final Supplier<EntityType<Dummy>> DUMMY = REGISTRY.register("dummy", () ->
            EntityType.Builder.<Dummy>of(Dummy::new, MobCategory.MISC)
                    .sized(0.625F, 2.0F)
                    .clientTrackingRange(10)
                    .build("dummy")
    );

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(MannequinsEntities.DUMMY.get(), Dummy.createAttributes().build());
    }
}
