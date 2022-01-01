package gg.moonflower.mannequins.core.registry;

import gg.moonflower.mannequins.common.item.MannequinItem;
import gg.moonflower.mannequins.core.Mannequins;
import gg.moonflower.pollen.api.registry.PollinatedRegistry;
import net.minecraft.core.Registry;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

public class MannequinsItems {
    public static final PollinatedRegistry<Item> ITEMS = PollinatedRegistry.create(Registry.ITEM, Mannequins.MOD_ID);

    public static final Supplier<Item> MANNEQUIN = ITEMS.register("mannequin", () -> new MannequinItem<>(MannequinsEntities.MANNEQUIN, new Item.Properties().stacksTo(16).tab(CreativeModeTab.TAB_DECORATIONS)));
    public static final Supplier<Item> STATUE = ITEMS.register("statue", () -> new MannequinItem<>(MannequinsEntities.STATUE, new Item.Properties().stacksTo(16).tab(CreativeModeTab.TAB_DECORATIONS)));

}
