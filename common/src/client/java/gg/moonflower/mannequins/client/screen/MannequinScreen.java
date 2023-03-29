package gg.moonflower.mannequins.client.screen;

import gg.moonflower.mannequins.common.entity.AbstractMannequin;
import gg.moonflower.mannequins.common.menu.MannequinInventoryMenu;
import gg.moonflower.mannequins.core.Mannequins;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class MannequinScreen extends AbstractMannequinScreen {
    public static final ResourceLocation TEXTURE = new ResourceLocation(Mannequins.MOD_ID, "textures/gui/container/mannequin.png");

    public MannequinScreen(MannequinInventoryMenu menu, Inventory inventory, AbstractMannequin mannequin) {
        super(menu, inventory, mannequin);
    }

    @Override
    public ResourceLocation getTexture() {
        return TEXTURE;
    }
}
