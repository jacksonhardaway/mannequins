package me.jaackson.mannequins.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import me.jaackson.mannequins.Mannequins;
import me.jaackson.mannequins.client.render.layer.MannequinArmorLayer;
import me.jaackson.mannequins.client.render.layer.MannequinElytraLayer;
import me.jaackson.mannequins.client.render.layer.MannequinHeadLayer;
import me.jaackson.mannequins.client.render.model.MannequinFullModel;
import me.jaackson.mannequins.client.render.model.MannequinModel;
import me.jaackson.mannequins.common.entity.Mannequin;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

/**
 * @author Ocelot, Jackson
 */
public class MannequinRenderer extends LivingEntityRenderer<Mannequin, MannequinModel> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(Mannequins.MOD_ID, "textures/entity/mannequin.png");

    public MannequinRenderer(EntityRenderDispatcher dispatcher) {
        super(dispatcher, new MannequinFullModel(), 0.0F);
        this.addLayer(new MannequinArmorLayer(this, new MannequinModel(0.5F), new MannequinModel(1.0F)));
        this.addLayer(new MannequinElytraLayer(this));
        this.addLayer(new MannequinHeadLayer(this));
        this.addLayer(new ItemInHandLayer<>(this));
    }

    @Override
    public ResourceLocation getTextureLocation(Mannequin entity) {
        return TEXTURE;
    }

    @Override
    protected boolean shouldShowName(Mannequin entity) {
        return super.shouldShowName(entity) && (entity.shouldShowName() || entity.hasCustomName() && entity == this.entityRenderDispatcher.crosshairPickEntity);
    }

    @Override
    protected void setupRotations(Mannequin entity, PoseStack poseStack, float f, float g, float partialTicks) {
        poseStack.mulPose(Vector3f.YP.rotationDegrees(180.0F - g));
        float i = (float) (entity.level.getGameTime() - entity.lastHit) + partialTicks;
        if (!entity.hasAnimation() && i < 5.0F) {
            poseStack.mulPose(Vector3f.YP.rotationDegrees(Mth.sin(i / 1.5F * (float) Math.PI) * 3.0F));
        }
    }
}