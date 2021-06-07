package me.jaackson.mannequins.client.screen.component;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import me.jaackson.mannequins.client.screen.MannequinScreen;
import me.jaackson.mannequins.client.util.ScrollHandler;
import me.jaackson.mannequins.client.util.ShapeRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.TickableWidget;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.Component;

/**
 * <p>Renders a scroll bar that can be scrolled between zero and a maximum value.</p>
 *
 * @author Ocelot
 */
public class ScrollBar extends AbstractWidget implements TickableWidget {
    private final ScrollHandler scrollHandler;
    private boolean dragging;

    public ScrollBar(int x, int y, int width, int height, int maxScroll, Component title) {
        super(x, y, width, height, title);
        this.scrollHandler = new ScrollHandler(0, height);
        this.setMaxScroll(maxScroll);
        this.dragging = false;
    }

    @Override
    public void tick() {
        this.scrollHandler.update();
    }

    @Override
    public void renderButton(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        Minecraft minecraft = Minecraft.getInstance();
        minecraft.getTextureManager().bind(MannequinScreen.TEXTURE);
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, this.alpha);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();

        this.renderBg(poseStack, minecraft, mouseX, mouseY);

        boolean draggable = this.scrollHandler.getMaxScroll() > 0;

        if (this.dragging)
            this.scrollHandler.setScroll(this.scrollHandler.getMaxScroll() * ((mouseY - this.y - 15 / 2F) / (double) (this.height - 15)));

        float barY = draggable ? (this.height - (2 + 15)) * this.scrollHandler.getInterpolatedScroll(partialTicks) / this.scrollHandler.getMaxScroll() : 0;
        ShapeRenderer.drawRectWithTexture(poseStack, this.x + 1, this.y + Math.round(barY) + 1, draggable ? 176 : 182, 0, 6, 15);
    }

    @Override
    public void playDownSound(SoundManager manager) {
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
    public boolean mouseScrolled(double mouseX, double mouseY, double delta) {
        this.scrollHandler.mouseScrolled(2.0F, delta);
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
}