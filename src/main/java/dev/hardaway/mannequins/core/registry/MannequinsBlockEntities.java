package dev.hardaway.mannequins.core.registry;

import dev.hardaway.mannequins.common.block.entity.MannequinBlockEntity;
import dev.hardaway.mannequins.common.block.entity.StatueBlockEntity;
import dev.hardaway.mannequins.core.Mannequins;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class MannequinsBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> REGISTRY = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, Mannequins.MOD_ID);

    public static final Supplier<BlockEntityType<MannequinBlockEntity>> MANNEQUIN = REGISTRY.register("mannequin", () -> BlockEntityType.Builder.of(MannequinBlockEntity::new, MannequinsBlocks.MANNEQUIN.get()).build(null));
    public static final Supplier<BlockEntityType<StatueBlockEntity>> STATUE = REGISTRY.register("statue", () -> BlockEntityType.Builder.of(StatueBlockEntity::new, MannequinsBlocks.STATUE.get()).build(null));
}
