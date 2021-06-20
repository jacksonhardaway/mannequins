package me.jaackson.mannequins;

import me.jaackson.mannequins.bridge.Platform;
import me.jaackson.mannequins.bridge.RegistryBridge;
import me.jaackson.mannequins.common.entity.Mannequin;
import me.jaackson.mannequins.common.item.MannequinItem;
import me.shedaniel.architectury.annotations.ExpectPlatform;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

public class MannequinsRegistry {

    public static final Supplier<Item> MANNEQUIN_ITEM = RegistryBridge.registerItem("mannequin", () -> new MannequinItem(new Item.Properties().stacksTo(16).tab(CreativeModeTab.TAB_DECORATIONS)));

    public static final Supplier<EntityType<Mannequin>> MANNEQUIN = RegistryBridge.registerEntity("mannequin", () -> EntityType.Builder.of(Mannequin::new, MobCategory.MISC).sized(0.5F, 2.0F).clientTrackingRange(10));

    public static final Supplier<SoundEvent> ENTITY_MANNEQUIN_BREAK = RegistryBridge.registerSound("entity.mannequin.break", () -> new SoundEvent(new ResourceLocation(Mannequins.MOD_ID, "entity.mannequin.break")));
    public static final Supplier<SoundEvent> ENTITY_MANNEQUIN_FALL = RegistryBridge.registerSound("entity.mannequin.fall", () -> new SoundEvent(new ResourceLocation(Mannequins.MOD_ID, "entity.mannequin.fall")));
    public static final Supplier<SoundEvent> ENTITY_MANNEQUIN_HIT = RegistryBridge.registerSound("entity.mannequin.hit", () -> new SoundEvent(new ResourceLocation(Mannequins.MOD_ID, "entity.mannequin.hit")));
    public static final Supplier<SoundEvent> ENTITY_MANNEQUIN_PLACE = RegistryBridge.registerSound("entity.mannequin.place", () -> new SoundEvent(new ResourceLocation(Mannequins.MOD_ID, "entity.mannequin.place")));

    @ExpectPlatform
    public static void register() {
        Platform.safeAssertionError();
    }
}
