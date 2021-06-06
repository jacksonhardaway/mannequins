package me.jaackson.mannequins.client.render.layer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import me.jaackson.mannequins.bridge.Platform;
import me.jaackson.mannequins.client.render.model.MannequinFullModel;
import me.jaackson.mannequins.client.render.model.MannequinModel;
import me.jaackson.mannequins.common.entity.Mannequin;
import me.shedaniel.architectury.annotations.ExpectPlatform;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.DyeableLeatherItem;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

/**
 * @author Ocelot
 */
public class MannequinArmorLayer extends HumanoidArmorLayer<Mannequin, MannequinModel, MannequinModel> {

    public MannequinArmorLayer(RenderLayerParent<Mannequin, MannequinModel> renderLayerParent, MannequinModel innerModel, MannequinModel outerModel) {
        super(renderLayerParent, innerModel, outerModel);
    }

    @ExpectPlatform
    public static MannequinModel getArmorModel(HumanoidArmorLayer<Mannequin, MannequinModel, MannequinModel> layer, Mannequin entity, ItemStack itemStack, EquipmentSlot slot, MannequinModel model) {
        return Platform.safeAssertionError();
    }

    @ExpectPlatform
    public static ResourceLocation getArmorTexture(HumanoidArmorLayer<Mannequin, MannequinModel, MannequinModel> layer, Entity entity, ItemStack stack, EquipmentSlot slot, boolean innerModel, @Nullable String type) {
        return Platform.safeAssertionError();
    }

    @Override
    public void renderArmorPiece(PoseStack poseStack, MultiBufferSource buffer, Mannequin entity, EquipmentSlot slot, int packedLight, MannequinModel model) {
        ItemStack stack = entity.getItemBySlot(slot);
        if (stack.getItem() instanceof ArmorItem) {
            ArmorItem armoritem = (ArmorItem) stack.getItem();
            if (armoritem.getSlot() == slot) {
                model = getArmorModel(this, entity, stack, slot, model);
                poseStack.pushPose();
                this.getParentModel().copyPropertiesTo(model);
                model.body.y += 2;
                model.body.xRot = 0;
                model.body.yRot = 0;
                model.body.zRot = 0;
                if (this.getParentModel() instanceof MannequinFullModel) {
                    ((MannequinFullModel) this.getParentModel()).translateToBody(poseStack);
                }
                this.setPartVisibility(model, slot);
                boolean flag1 = stack.hasFoil();
                if (armoritem instanceof DyeableLeatherItem) {
                    int i = ((DyeableLeatherItem) armoritem).getColor(stack);
                    float f = (float) (i >> 16 & 255) / 255.0F;
                    float f1 = (float) (i >> 8 & 255) / 255.0F;
                    float f2 = (float) (i & 255) / 255.0F;
                    this.renderModel(poseStack, buffer, packedLight, flag1, model, f, f1, f2, getArmorTexture(this, entity, stack, slot, this.usesInnerModel(slot), null));
                    this.renderModel(poseStack, buffer, packedLight, flag1, model, 1.0F, 1.0F, 1.0F, getArmorTexture(this, entity, stack, slot, this.usesInnerModel(slot), "overlay"));
                } else {
                    this.renderModel(poseStack, buffer, packedLight, flag1, model, 1.0F, 1.0F, 1.0F, getArmorTexture(this, entity, stack, slot, this.usesInnerModel(slot), null));
                }
                poseStack.popPose();
            }
        }

    }

    protected void setPartVisibility(MannequinModel model, EquipmentSlot slot) {
        model.setAllVisible(false);
        switch (slot) {
            case HEAD:
                model.head.visible = true;
                model.hat.visible = true;
                break;
            case CHEST:
                model.body.visible = true;
                model.rightArm.visible = true;
                model.leftArm.visible = true;
                break;
            case LEGS:
                model.body.visible = true;
                model.rightLeg.visible = true;
                model.leftLeg.visible = true;
                break;
            case FEET:
                model.rightLeg.visible = true;
                model.leftLeg.visible = true;
        }

    }

    private void renderModel(PoseStack poseStack, MultiBufferSource buffer, int packedLight, boolean p_241738_5_, MannequinModel model, float red, float green, float blue, ResourceLocation texture) {
        VertexConsumer vertexConsumer = ItemRenderer.getArmorFoilBuffer(buffer, RenderType.armorCutoutNoCull(texture), false, p_241738_5_);
        model.renderToBuffer(poseStack, vertexConsumer, packedLight, OverlayTexture.NO_OVERLAY, red, green, blue, 1.0F);
    }
}