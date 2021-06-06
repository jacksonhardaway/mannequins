package me.jaackson.mannequins.client.render.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import me.jaackson.mannequins.bridge.Platform;
import me.jaackson.mannequins.client.render.model.MannequinFullModel;
import me.jaackson.mannequins.client.render.model.MannequinModel;
import me.jaackson.mannequins.common.entity.Mannequin;
import me.shedaniel.architectury.annotations.ExpectPlatform;
import net.minecraft.client.model.ElytraModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.ElytraLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;

/**
 * @author Ocelot
 */
public class MannequinElytraLayer extends ElytraLayer<Mannequin, MannequinModel> {
    private final ElytraModel<Mannequin> elytraModel = new ElytraModel<>();

    public MannequinElytraLayer(RenderLayerParent<Mannequin, MannequinModel> renderLayerParent) {
        super(renderLayerParent);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffers, int packedLight, Mannequin mannequin, float p_225628_5_, float p_225628_6_, float p_225628_7_, float p_225628_8_, float p_225628_9_, float p_225628_10_) {
        ItemStack stack = mannequin.getItemBySlot(EquipmentSlot.CHEST);
        if (canRender(this, stack, mannequin)) {
            ResourceLocation texture = getTexture(this, stack, mannequin);

            poseStack.pushPose();
            this.getParentModel().copyPropertiesTo(this.elytraModel);
            if (this.getParentModel() instanceof MannequinFullModel) {
                ((MannequinFullModel) this.getParentModel()).translateToElytra(poseStack);
            }
            poseStack.translate(0.0D, 0.0D, 0.125D);
            this.elytraModel.setupAnim(mannequin, p_225628_5_, p_225628_6_, p_225628_8_, p_225628_9_, p_225628_10_);
            VertexConsumer vertex = ItemRenderer.getArmorFoilBuffer(buffers, RenderType.armorCutoutNoCull(texture), false, stack.hasFoil());
            this.elytraModel.renderToBuffer(poseStack, vertex, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
            poseStack.popPose();
        }
    }

    @ExpectPlatform
    public static boolean canRender(ElytraLayer<Mannequin, MannequinModel> layer, ItemStack stack, Mannequin entity) {
        return Platform.safeAssertionError();
    }

    @ExpectPlatform
    public static ResourceLocation getTexture(ElytraLayer<Mannequin, MannequinModel> layer, ItemStack stack, Mannequin entity) {
        return Platform.safeAssertionError();
    }
}