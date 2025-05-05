package dev.hardaway.mannequins.client.screen.widget;

import com.mojang.blaze3d.systems.RenderSystem;
import dev.hardaway.mannequins.core.Mannequins;
import dev.hardaway.mannequins.core.util.ScrollHandler;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.WidgetSprites;
import net.minecraft.client.gui.narration.NarrationElementOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class ScrollBar extends AbstractWidget {
    private static final ResourceLocation SCROLLBAR = Mannequins.path("widget/scrollbar");
    private static final ResourceLocation SCROLLBAR_DISABLED = Mannequins.path("widget/scrollbar_disabled");

    private final ScrollHandler scrollHandler;
    private boolean dragging;

    public ScrollBar(int x, int y, int width, int height, int maxScroll, Component title) {
        super(x, y, width, height, title);
        this.scrollHandler = new ScrollHandler(0, height);
        this.setMaxScroll(maxScroll);
        this.dragging = false;
    }

    public void tick() {
        this.scrollHandler.update();
    }

    @Override
    public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        guiGraphics.setColor(1.0F, 1.0F, 1.0F, this.alpha);
        RenderSystem.enableBlend();
        RenderSystem.enableDepthTest();

        boolean draggable = this.scrollHandler.getMaxScroll() > 0;
        if (this.dragging)
            this.scrollHandler.setScroll(this.scrollHandler.getMaxScroll() * ((mouseY - this.getY() - 15 / 2F) / (double) (this.height - 15)));

        float barY = draggable ? (this.height - (2 + 15)) * this.scrollHandler.getInterpolatedScroll(partialTicks) / this.scrollHandler.getMaxScroll() : 0;

        guiGraphics.blitSprite(this.active ? SCROLLBAR : SCROLLBAR_DISABLED, this.getX() + 1, this.getY() + Math.round(barY) + 1, 6, 15);
        guiGraphics.setColor(1.0F, 1.0F, 1.0F, 1.0F);
    }

    @Override
    public void onClick(double mouseX, double mouseY) {
        this.dragging = true;
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if (this.dragging) {
            this.dragging = false;
            return true;
        }
        return false;
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double scrollX, double scrollY) {
        this.scrollHandler.mouseScrolled(2.0F, scrollY);
        return true;
    }

    /**
     * Sets the amount of scroll that is added each scrol.
     *
     * @param speed The new speed of scroll
     */
    public void setScrollSpeed(float speed) {
        this.scrollHandler.setScrollSpeed(speed);
    }

    /**
     * @return The percent this bar is scrolled to
     */
    public float getInterpolatedScrollPercentage(float partialTicks) {
        return this.scrollHandler.getMaxScroll() > 0 ? (this.scrollHandler.getInterpolatedScroll(partialTicks) / this.scrollHandler.getMaxScroll()) : 0;
    }

    /**
     * @return The percent this bar is scrolled to
     */
    public float getScrollPercentage() {
        return this.scrollHandler.getMaxScroll() > 0 ? (float) (this.scrollHandler.getScroll() / this.scrollHandler.getMaxScroll()) : 0;
    }

    /**
     * @return The amount this slider is scrolled to
     */
    public double getScroll() {
        return this.scrollHandler.getMaxScroll() > 0 ? this.scrollHandler.getScroll() : 0;
    }

    /**
     * Sets the scroll amount for the scroll bar.
     *
     * @param scroll The new scroll value
     */
    public void setScroll(double scroll) {
        this.scrollHandler.setScroll(scroll);
    }

    /**
     * @return The amount this slider is scrolled to for rendering
     */
    public float getInterpolatedScroll(float partialTicks) {
        return this.scrollHandler.getMaxScroll() > 0 ? this.scrollHandler.getInterpolatedScroll(partialTicks) : 0;
    }

    /**
     * @return The maximum amount this slider can scroll to
     */
    public int getMaxScroll() {
        return this.scrollHandler.getMaxScroll();
    }

    /**
     * Sets the maximum amount this slider can be scrolled to.
     *
     * @param maxScroll The new maximum value for scroll
     */
    public void setMaxScroll(int maxScroll) {
        this.scrollHandler.setHeight(maxScroll);
    }

    @Override
    protected void updateWidgetNarration(NarrationElementOutput narrationElementOutput) {
    }
}