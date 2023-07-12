package gg.moonflower.mannequins.client.mixin;

import gg.moonflower.mannequins.client.entity.ClientMannequin;
import gg.moonflower.mannequins.client.screen.AbstractMannequinScreen;
import gg.moonflower.mannequins.client.screen.StatueScreen;
import gg.moonflower.mannequins.common.entity.Statue;
import gg.moonflower.mannequins.common.menu.MannequinInventoryMenu;
import net.minecraft.world.entity.player.Inventory;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Statue.class)
public class StatueMixin implements ClientMannequin {
    @Override
    public AbstractMannequinScreen getScreen(MannequinInventoryMenu menu, Inventory inventory) {
        return new StatueScreen(menu, inventory, (Statue) (Object) this);
    }
}
