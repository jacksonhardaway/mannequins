package gg.moonflower.mannequins.core.registry;

import gg.moonflower.mannequins.core.Mannequins;
import gg.moonflower.pollen.api.registry.PollinatedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

import java.util.function.Supplier;

public class MannequinsSounds {
    public static final PollinatedRegistry<SoundEvent> SOUNDS = PollinatedRegistry.create(Registry.SOUND_EVENT, Mannequins.MOD_ID);

    public static final Supplier<SoundEvent> ENTITY_MANNEQUIN_BREAK = registerSound("entity.mannequin.break");
    public static final Supplier<SoundEvent> ENTITY_MANNEQUIN_FALL = registerSound("entity.mannequin.fall");
    public static final Supplier<SoundEvent> ENTITY_MANNEQUIN_HIT = registerSound("entity.mannequin.hit");
    public static final Supplier<SoundEvent> ENTITY_MANNEQUIN_PLACE = registerSound("entity.mannequin.place");

    public static final Supplier<SoundEvent> ENTITY_STATUE_BREAK = registerSound("entity.statue.break");
    public static final Supplier<SoundEvent> ENTITY_STATUE_FALL = registerSound("entity.statue.fall");
    public static final Supplier<SoundEvent> ENTITY_STATUE_HIT = registerSound("entity.statue.hit");
    public static final Supplier<SoundEvent> ENTITY_STATUE_PLACE = registerSound("entity.statue.place");

    private static Supplier<SoundEvent> registerSound(String id) {
        return SOUNDS.register(id, () -> new SoundEvent(new ResourceLocation(Mannequins.MOD_ID, id)));
    }
}
