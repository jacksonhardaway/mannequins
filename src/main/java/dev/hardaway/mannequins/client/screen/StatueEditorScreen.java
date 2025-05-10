package dev.hardaway.mannequins.client.screen;

import dev.hardaway.mannequins.common.menu.DummyEditorMenu;
import dev.hardaway.mannequins.core.Mannequins;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class StatueEditorScreen extends DummyEditorScreen {
    private static final ResourceLocation BG_LOCATION = Mannequins.path("textures/gui/container/statue.png");


    public StatueEditorScreen(DummyEditorMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
    }

    @Override
    protected ResourceLocation getBgTextureLocation() {
        return BG_LOCATION;
    }
}
