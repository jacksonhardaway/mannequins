package dev.hardaway.mannequins.core.data;

import dev.hardaway.mannequins.core.registry.MannequinsItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;

import java.util.concurrent.CompletableFuture;

public class MannequinsRecipeProvider extends RecipeProvider {
    public MannequinsRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, MannequinsItems.MANNEQUIN)
                .define('/', Items.STICK)
                .define('P', Blocks.OAK_PLANKS)
                .define('_', Blocks.SMOOTH_STONE_SLAB)
                .pattern(" P ")
                .pattern("///")
                .pattern(" _ ")
                .unlockedBy("has_stone_slab", has(Blocks.SMOOTH_STONE_SLAB))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, MannequinsItems.STATUE)
                .define('S', Blocks.STONE)
                .define('_', Blocks.SMOOTH_STONE_SLAB)
                .pattern(" S ")
                .pattern("SSS")
                .pattern(" _ ")
                .unlockedBy("has_stone_slab", has(Blocks.SMOOTH_STONE_SLAB))
                .save(recipeOutput);
    }
}
