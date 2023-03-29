package gg.moonflower.mannequins.client.mixin;

import gg.moonflower.mannequins.client.entity.ClientMannequin;
import gg.moonflower.mannequins.client.screen.AbstractMannequinScreen;
import gg.moonflower.mannequins.client.screen.MannequinScreen;
import gg.moonflower.mannequins.common.entity.Mannequin;
import gg.moonflower.mannequins.common.menu.MannequinInventoryMenu;
import net.minecraft.world.entity.player.Inventory;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Mannequin.class)
public class MannequinMixin implements ClientMannequin {
    @Override
    public AbstractMannequinScreen getScreen(MannequinInventoryMenu menu, Inventory inventory) {
        return new MannequinScreen(menu, inventory, (Mannequin) (Object) this);
    }
}
