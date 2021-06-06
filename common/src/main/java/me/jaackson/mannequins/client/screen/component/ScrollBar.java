package me.jaackson.mannequins.client.screen.component;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import me.jaackson.mannequins.Mannequins;
import me.jaackson.mannequins.client.screen.MannequinsScreenSpriteManager;
import me.jaackson.mannequins.client.util.RenderHelper;
import me.jaackson.mannequins.client.util.ScrollHandler;
import me.jaackson.mannequins.client.util.ShapeRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.TickableWidget;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.sounds.SoundManager;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

/**
 * <p>Renders a scroll bar that can be scrolled between zero and a maximum value.</p>
 *
 * @author Ocelot
 */
public class ScrollBar extends AbstractWidget implements TickableWidget {
    private static final ResourceLocation SLOT = new ResourceLocation(Mannequins.MOD_ID, "component/slot");
    private static final ResourceLocation SCROLL_BAR_TEXTURE = new ResourceLocation(Mannequins.MOD_ID, "component/scroll_bar");
    private static final ResourceLocation SELECTED_SCROLL_BAR_TEXTURE = new ResourceLocation(Mannequins.MOD_ID, "component/selected_scroll_bar");

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
        minecraft.getTextureManager().bind(MannequinsScreenSpriteManager.ATLAS_LOCATION);
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, this.alpha);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();

        VertexConsumer builder = ShapeRenderer.begin();
        poseStack.pushPose();
        {
            RenderHelper.renderExpanding(builder, poseStack, this.x, this.y, this.width, this.height, MannequinsScreenSpriteManager.getSprite(SLOT));
            this.renderBg(poseStack, minecraft, mouseX, mouseY);
        }
        poseStack.popPose();

        boolean draggable = this.scrollHandler.getMaxScroll() > 0;
        TextureAtlasSprite sprite = MannequinsScreenSpriteManager.getSprite(draggable ? SELECTED_SCROLL_BAR_TEXTURE : SCROLL_BAR_TEXTURE);

        if (this.dragging)
            this.scrollHandler.setScroll(this.scrollHandler.getMaxScroll() * ((mouseY - this.y - sprite.getHeight() / 2F) / (double) (this.height - sprite.getHeight())));

        float barY = draggable ? (this.height - (2 + sprite.getHeight())) * this.scrollHandler.getInterpolatedScroll(partialTicks) / this.scrollHandler.getMaxScroll() : 0;
        drawSlider(builder, poseStack, this.x + 1, this.y + Math.round(barY) + 1, sprite);
        ShapeRenderer.end();
    }

    private void drawSlider(VertexConsumer builder, PoseStack poseStack, float x, float y, TextureAtlasSprite sprite) {
        float u = sprite.getU0();
        float v = sprite.getV0();
        float textureWidth = sprite.getU1() - sprite.getU0();
        float textureHeight = sprite.getV1() - sprite.getV0();
        float pixelWidth = textureWidth / 5;
        ShapeRenderer.drawRectWithTexture(builder, poseStack, x, y, u, v, 2, sprite.getHeight(), pixelWidth * 2, textureHeight, 1, 1);
        ShapeRenderer.drawRectWithTexture(builder, poseStack, x + 2, y, u + 2 * pixelWidth, v, this.width - 6, 15, pixelWidth, textureHeight, 1, 1);
        ShapeRenderer.drawRectWithTexture(builder, poseStack, x + this.width - 4, y, u + 3 * pixelWidth, v, 2, 15, pixelWidth * 2, textureHeight, 1, 1);
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