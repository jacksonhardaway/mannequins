package gg.moonflower.mannequins.client.entity;

import gg.moonflower.mannequins.client.screen.AbstractMannequinScreen;
import gg.moonflower.mannequins.common.menu.MannequinInventoryMenu;
import net.minecraft.world.entity.player.Inventory;

public interface ClientMannequin {

    AbstractMannequinScreen getScreen(MannequinInventoryMenu menu, Inventory inventory);
}
