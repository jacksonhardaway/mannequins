package dev.hardaway.mannequins.core.registry;

import dev.hardaway.mannequins.common.block.MannequinBlock;
import dev.hardaway.mannequins.common.block.StatueBlock;
import dev.hardaway.mannequins.core.Mannequins;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class MannequinsBlocks {

    public static final DeferredRegister.Blocks REGISTRY = DeferredRegister.createBlocks(Mannequins.MOD_ID);

    public static final DeferredBlock<MannequinBlock> MANNEQUIN = REGISTRY.registerBlock("mannequin", MannequinBlock::new,
            BlockBehaviour.Properties.of()
                    .noOcclusion()
                    .mapColor(Blocks.OAK_PLANKS.defaultMapColor())
                    .strength(0.2F)
                    .pushReaction(PushReaction.DESTROY)
                    .sound(MannequinsSounds.MANNEQUIN)
    );

    public static final DeferredBlock<StatueBlock> STATUE = REGISTRY.registerBlock("statue", StatueBlock::new,
            BlockBehaviour.Properties.of()
                    .noOcclusion()
                    .mapColor(Blocks.STONE.defaultMapColor())
                    .pushReaction(PushReaction.BLOCK)
                    .strength(1.0F)
                    .sound(MannequinsSounds.STATUE)
    );
}
