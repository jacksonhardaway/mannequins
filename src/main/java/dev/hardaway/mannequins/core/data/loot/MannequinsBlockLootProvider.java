package dev.hardaway.mannequins.core.data.loot;

import dev.hardaway.mannequins.common.block.MannequinBlock;
import dev.hardaway.mannequins.core.registry.MannequinsBlocks;
import dev.hardaway.mannequins.core.registry.MannequinsComponents;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.DynamicLoot;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.CopyComponentsFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import java.util.Set;

public class MannequinsBlockLootProvider extends BlockLootSubProvider {
    public MannequinsBlockLootProvider(HolderLookup.Provider registries) {
        super(Set.of(), FeatureFlags.DEFAULT_FLAGS, registries);
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return MannequinsBlocks.REGISTRY.getEntries()
                .stream()
                .map(e -> (Block) e.value())
                .toList();
    }

    @Override
    protected void generate() {
        this.add(MannequinsBlocks.MANNEQUIN.get(), LootTable.lootTable()
                .withPool(
                        this.applyExplosionCondition(
                                MannequinsBlocks.MANNEQUIN.get(),
                                LootPool.lootPool()
                                        .setRolls(ConstantValue.exactly(1.0F))
                                        .add(
                                                LootItem.lootTableItem(MannequinsBlocks.MANNEQUIN.get())
                                                        .when(
                                                                LootItemBlockStatePropertyCondition.hasBlockStateProperties(MannequinsBlocks.MANNEQUIN.get())
                                                                        .setProperties(StatePropertiesPredicate.Builder.properties()
                                                                                .hasProperty(MannequinBlock.HALF, DoubleBlockHalf.LOWER)
                                                                        )
                                                        )
                                                        .apply(
                                                                CopyComponentsFunction.copyComponents(CopyComponentsFunction.Source.BLOCK_ENTITY)
                                                        )
                                        )
                        )
                )
                .withPool( // Always drop mannequin inventory
                        LootPool.lootPool()
                                .setRolls(ConstantValue.exactly(1.0F))
                                .add(DynamicLoot.dynamicEntry(MannequinBlock.MANNEQUIN_INVENTORY)
                                        .when(
                                                LootItemBlockStatePropertyCondition.hasBlockStateProperties(MannequinsBlocks.MANNEQUIN.get())
                                                        .setProperties(StatePropertiesPredicate.Builder.properties()
                                                                .hasProperty(MannequinBlock.HALF, DoubleBlockHalf.LOWER)
                                                        )
                                        ))
                ));
    }
}
