package dev.hardaway.mannequins.core.registry;

import dev.hardaway.mannequins.common.block.entity.DummyBlockEntity;
import dev.hardaway.mannequins.core.Mannequins;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class MannequinsBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> REGISTRY = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, Mannequins.MOD_ID);

    public static final Supplier<BlockEntityType<DummyBlockEntity>> DUMMY = REGISTRY.register("dummy", () -> BlockEntityType.Builder.of(DummyBlockEntity::new, MannequinsBlocks.MANNEQUIN.get(), MannequinsBlocks.STATUE.get()).build(null));
}
