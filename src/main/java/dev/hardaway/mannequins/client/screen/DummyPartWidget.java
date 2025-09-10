package dev.hardaway.mannequins.client.screen;

import dev.hardaway.mannequins.core.Mannequins;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;

public class DummyPartWidget extends AbstractButton {

    private static final WidgetSprites DUMMY_PART_BUTTON_SPRITES = new WidgetSprites(
            Mannequins.path("container/editor/dummy_part_button"),
            Mannequins.path("container/editor/dummy_part_button_disabled"),
            Mannequins.path("container/editor/dummy_part_button_highlighted")
    );

    private final DummyEditorScreen editor;
    protected final DummyEditorScreen.Part part;

    public DummyPartWidget(DummyEditorScreen editor, DummyEditorScreen.Part part, int x, int y, int width, int height) {
        super(x, y, width, height, part.getName());
        this.editor = editor;
        this.part = part;
        this.setTooltip(Tooltip.create(this.getMessage()));
    }

    @Override
    protected void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        guiGraphics.blitSprite(DUMMY_PART_BUTTON_SPRITES.get(this.active, this.isHoveredOrFocused()), this.getX(), this.getY(), this.getWidth(), this.getHeight());
    }

    @Override
    public void onPress() {
        this.editor.selectPart(this);
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {
        this.defaultButtonNarrationText(narrationElementOutput);
    }
}
