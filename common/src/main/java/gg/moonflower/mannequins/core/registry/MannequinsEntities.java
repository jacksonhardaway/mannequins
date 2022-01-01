package gg.moonflower.mannequins.core.registry;

import gg.moonflower.mannequins.common.entity.Mannequin;
import gg.moonflower.mannequins.common.entity.Statue;
import gg.moonflower.mannequins.core.Mannequins;
import gg.moonflower.pollen.api.registry.PollinatedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

import java.util.function.Supplier;

public class MannequinsEntities {
    public static final PollinatedRegistry<EntityType<?>> ENTITIES = PollinatedRegistry.create(Registry.ENTITY_TYPE, Mannequins.MOD_ID);

    public static final Supplier<EntityType<Mannequin>> MANNEQUIN = ENTITIES.register("mannequin", () -> EntityType.Builder.of(Mannequin::new, MobCategory.MISC).sized(0.5F, 2.0F).clientTrackingRange(10).build("mannequin"));
    public static final Supplier<EntityType<Statue>> STATUE = ENTITIES.register("statue", () -> EntityType.Builder.of(Statue::new, MobCategory.MISC).sized(0.5F, 2.0F).clientTrackingRange(10).build("statue"));
}
