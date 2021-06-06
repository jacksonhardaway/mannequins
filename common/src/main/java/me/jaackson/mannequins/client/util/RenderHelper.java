package me.jaackson.mannequins.client.util;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;

/**
 * @author Ocelot
 */
public class RenderHelper
{
    /**
     * Renders an expanding square with a grid texture.
     *
     * @param poseStack    The stack of transformations to apply
     * @param x            The x position of the square
     * @param y            The y position of the square
     * @param u            The x position of the top left texture square
     * @param v            The y position of the top left texture square
     * @param width        The width of the overall square
     * @param height       The height of the overall square
     * @param cellSize     The size of each cell in the grid
     * @param sourceWidth  The width of the texture image
     * @param sourceHeight The height of the texture image
     */
    @Deprecated
    public static void renderExpanding(PoseStack poseStack, float x, float y, int u, int v, float width, float height, int cellSize, int sourceWidth, int sourceHeight)
    {
        VertexConsumer builder = ShapeRenderer.begin();
        ShapeRenderer.drawRectWithTexture(builder, poseStack, x, y, u, v, cellSize, cellSize, cellSize, cellSize, sourceWidth, sourceHeight);
        ShapeRenderer.drawRectWithTexture(builder, poseStack, x, y + cellSize, u, v + cellSize, cellSize, height - 2 * cellSize, cellSize, cellSize, sourceWidth, sourceHeight);
        ShapeRenderer.drawRectWithTexture(builder, poseStack, x, y + height - cellSize, u, v + 2 * cellSize, cellSize, cellSize, cellSize, cellSize, sourceWidth, sourceHeight);
        ShapeRenderer.drawRectWithTexture(builder, poseStack, x + cellSize, y, u + cellSize, v, width - 2 * cellSize, cellSize, cellSize, cellSize, sourceWidth, sourceHeight);
        ShapeRenderer.drawRectWithTexture(builder, poseStack, x + cellSize, y + cellSize, u + cellSize, v + cellSize, width - 2 * cellSize, height - 2 * cellSize, cellSize, cellSize, sourceWidth, sourceHeight);
        ShapeRenderer.drawRectWithTexture(builder, poseStack, x + cellSize, y + height - cellSize, u + cellSize, v + 2 * cellSize, width - 2 * cellSize, cellSize, cellSize, cellSize, sourceWidth, sourceHeight);
        ShapeRenderer.drawRectWithTexture(builder, poseStack, x + width - cellSize, y, u + 2 * cellSize, v, cellSize, cellSize, cellSize, cellSize, sourceWidth, sourceHeight);
        ShapeRenderer.drawRectWithTexture(builder, poseStack, x + width - cellSize, y + cellSize, u + 2 * cellSize, v + cellSize, cellSize, height - 2 * cellSize, cellSize, cellSize, sourceWidth, sourceHeight);
        ShapeRenderer.drawRectWithTexture(builder, poseStack, x + width - cellSize, y + height - cellSize, u + 2 * cellSize, v + 2 * cellSize, cellSize, cellSize, cellSize, cellSize, sourceWidth, sourceHeight);
        ShapeRenderer.end();
    }

    /**
     * Renders an expanding square with a grid texture.
     *
     * @param poseStack The stack of transformations to apply
     * @param x         The x position of the square
     * @param y         The y position of the square
     * @param width     The width of the overall square
     * @param height    The height of the overall square
     * @param sprite    The sprite to use as the grid
     */
    public static void renderExpanding(PoseStack poseStack, float x, float y, float width, float height, TextureAtlasSprite sprite)
    {
        renderExpanding(ShapeRenderer.begin(), poseStack, x, y, width, height, sprite);
        ShapeRenderer.end();
    }

    /**
     * Renders an expanding square with a grid texture.
     *
     * @param builder   The builder for batching
     * @param poseStack The stack of transformations to apply
     * @param x         The x position of the square
     * @param y         The y position of the square
     * @param width     The width of the overall square
     * @param height    The height of the overall square
     * @param sprite    The sprite to use as the grid
     */
    public static void renderExpanding(VertexConsumer builder, PoseStack poseStack, float x, float y, float width, float height, TextureAtlasSprite sprite)
    {
        float u = sprite.getU0();
        float v = sprite.getV0();
        float cellWidth = sprite.getWidth() / 3F;
        float cellHeight = sprite.getHeight() / 3F;
        float cellTextureWidth = (sprite.getU1() - sprite.getU0()) / 3F;
        float cellTextureHeight = (sprite.getV1() - sprite.getV0()) / 3F;
        ShapeRenderer.drawRectWithTexture(builder, poseStack, x, y, u, v, cellWidth, cellHeight, cellTextureWidth, cellTextureHeight, 1, 1);
        ShapeRenderer.drawRectWithTexture(builder, poseStack, x, y + cellHeight, u, v + cellTextureHeight, cellWidth, height - 2 * cellHeight, cellTextureWidth, cellTextureHeight, 1, 1);
        ShapeRenderer.drawRectWithTexture(builder, poseStack, x, y + height - cellHeight, u, v + 2 * cellTextureHeight, cellWidth, cellHeight, cellTextureWidth, cellTextureHeight, 1, 1);
        ShapeRenderer.drawRectWithTexture(builder, poseStack, x + cellWidth, y, u + cellTextureWidth, v, width - 2 * cellWidth, cellHeight, cellTextureWidth, cellTextureHeight, 1, 1);
        ShapeRenderer.drawRectWithTexture(builder, poseStack, x + cellWidth, y + cellHeight, u + cellTextureWidth, v + cellTextureHeight, width - 2 * cellWidth, height - 2 * cellHeight, cellTextureWidth, cellTextureHeight, 1, 1);
        ShapeRenderer.drawRectWithTexture(builder, poseStack, x + cellWidth, y + height - cellHeight, u + cellTextureWidth, v + 2 * cellTextureHeight, width - 2 * cellWidth, cellHeight, cellTextureWidth, cellTextureHeight, 1, 1);
        ShapeRenderer.drawRectWithTexture(builder, poseStack, x + width - cellWidth, y, u + 2 * cellTextureWidth, v, cellWidth, cellHeight, cellTextureWidth, cellTextureHeight, 1, 1);
        ShapeRenderer.drawRectWithTexture(builder, poseStack, x + width - cellWidth, y + cellHeight, u + 2 * cellTextureWidth, v + cellTextureHeight, cellWidth, height - 2 * cellHeight, cellTextureWidth, cellTextureHeight, 1, 1);
        ShapeRenderer.drawRectWithTexture(builder, poseStack, x + width - cellWidth, y + height - cellHeight, u + 2 * cellTextureWidth, v + 2 * cellTextureHeight, cellWidth, cellHeight, cellTextureWidth, cellTextureHeight, 1, 1);
    }
}