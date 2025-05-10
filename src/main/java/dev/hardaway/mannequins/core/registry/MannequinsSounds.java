package dev.hardaway.mannequins.core.registry;

import dev.hardaway.mannequins.core.Mannequins;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.SoundType;
import net.neoforged.neoforge.common.util.DeferredSoundType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class MannequinsSounds {
    public static final DeferredRegister<SoundEvent> REGISTRY = DeferredRegister.create(Registries.SOUND_EVENT, Mannequins.MOD_ID);

    public static final Supplier<SoundEvent> BLOCK_MANNEQUIN_BREAK = registerSound("block.editor.break");
    public static final Supplier<SoundEvent> BLOCK_MANNEQUIN_HIT = registerSound("block.editor.hit");
    public static final Supplier<SoundEvent> BLOCK_MANNEQUIN_PLACE = registerSound("block.editor.place");

    public static final Supplier<SoundEvent> BLOCK_STATUE_BREAK = registerSound("block.statue.break");
    public static final Supplier<SoundEvent> BLOCK_STATUE_HIT = registerSound("block.statue.hit");
    public static final Supplier<SoundEvent> BLOCK_STATUE_PLACE = registerSound("block.statue.place");


    public static final SoundType MANNEQUIN = new DeferredSoundType(
            1.0F,
            1.0F,
            MannequinsSounds.BLOCK_MANNEQUIN_BREAK,
            () -> SoundEvents.EMPTY,
            MannequinsSounds.BLOCK_MANNEQUIN_PLACE,
            MannequinsSounds.BLOCK_MANNEQUIN_HIT,
            () -> SoundEvents.EMPTY
    );

    public static final SoundType STATUE = new DeferredSoundType(
            1.0F,
            1.0F,
            MannequinsSounds.BLOCK_STATUE_BREAK,
            () -> SoundEvents.EMPTY,
            MannequinsSounds.BLOCK_STATUE_PLACE,
            MannequinsSounds.BLOCK_STATUE_HIT,
            () -> SoundEvents.EMPTY
    );

    private static Supplier<SoundEvent> registerSound(String id) {
        return REGISTRY.register(id, () -> SoundEvent.createVariableRangeEvent(Mannequins.path(id)));
    }
}