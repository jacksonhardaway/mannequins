package gg.moonflower.mannequins.core;

import gg.moonflower.mannequins.common.entity.Mannequin;
import gg.moonflower.mannequins.common.item.MannequinItem;
import gg.moonflower.pollen.api.registry.PollinatedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

public class MannequinsRegistry {

    public static final PollinatedRegistry<Item> ITEMS = PollinatedRegistry.create(Registry.ITEM, Mannequins.MOD_ID);
    public static final PollinatedRegistry<EntityType<?>> ENTITIES = PollinatedRegistry.create(Registry.ENTITY_TYPE, Mannequins.MOD_ID);
    public static final PollinatedRegistry<SoundEvent> SOUNDS = PollinatedRegistry.create(Registry.SOUND_EVENT, Mannequins.MOD_ID);

    public static final Supplier<Item> MANNEQUIN_ITEM = ITEMS.register("mannequin", () -> new MannequinItem(new Item.Properties().stacksTo(16).tab(CreativeModeTab.TAB_DECORATIONS)));
    public static final Supplier<EntityType<Mannequin>> MANNEQUIN = ENTITIES.register("mannequin", () -> EntityType.Builder.of(Mannequin::new, MobCategory.MISC).sized(0.5F, 2.0F).clientTrackingRange(10).build("mannequin"));

    public static final Supplier<SoundEvent> ENTITY_MANNEQUIN_BREAK = registerSound("entity.mannequin.break");
    public static final Supplier<SoundEvent> ENTITY_MANNEQUIN_FALL = registerSound("entity.mannequin.fall");
    public static final Supplier<SoundEvent> ENTITY_MANNEQUIN_HIT = registerSound("entity.mannequin.hit");
    public static final Supplier<SoundEvent> ENTITY_MANNEQUIN_PLACE = registerSound("entity.mannequin.place");

    private static Supplier<SoundEvent> registerSound(String id) {
        return SOUNDS.register(id, () -> new SoundEvent(new ResourceLocation(Mannequins.MOD_ID, id)));
    }
}
