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

    public static final Supplier<SoundEvent> BLOCK_MANNEQUIN_BREAK = registerSound("block.mannequin.break");
    public static final Supplier<SoundEvent> BLOCK_MANNEQUIN_HIT = registerSound("block.mannequin.hit");
    public static final Supplier<SoundEvent> BLOCK_MANNEQUIN_PLACE = registerSound("block.mannequin.place");

    public static final SoundType MANNEQUIN = new DeferredSoundType(
            1.0F,
            1.0F,
            MannequinsSounds.BLOCK_MANNEQUIN_BREAK,
            () -> SoundEvents.EMPTY,
            MannequinsSounds.BLOCK_MANNEQUIN_PLACE,
            MannequinsSounds.BLOCK_MANNEQUIN_HIT,
            () -> SoundEvents.EMPTY
    );

    private static Supplier<SoundEvent> registerSound(String id) {
        return REGISTRY.register(id, () -> SoundEvent.createVariableRangeEvent(Mannequins.path(id)));
    }
}