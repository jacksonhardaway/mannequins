package dev.hardaway.mannequins.client.render.entity;

import dev.hardaway.mannequins.client.model.DummyModel;
import dev.hardaway.mannequins.client.model.MannequinModel;
import dev.hardaway.mannequins.common.entity.Dummy;
import dev.hardaway.mannequins.core.Mannequins;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.client.renderer.entity.layers.ElytraLayer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.resources.ResourceLocation;

public class DummyEntityRenderer extends LivingEntityRenderer<Dummy, DummyModel> {
    public DummyEntityRenderer(EntityRendererProvider.Context context, DummyModel model, DummyModel innerArmorModel, DummyModel outerArmorModel) {
        super(context, model, 0.0F);
        this.addLayer(new HumanoidArmorLayer<>(this, innerArmorModel, outerArmorModel, context.getModelManager()));
        this.addLayer(new ElytraLayer<>(this, context.getModelSet()));
        this.addLayer(new CustomHeadLayer<>(this, context.getModelSet(), context.getItemInHandRenderer()));
        this.addLayer(new ItemInHandLayer<>(this, context.getItemInHandRenderer()));
    }

    @Override
    protected boolean shouldShowName(Dummy entity) {
        return super.shouldShowName(entity) && (entity.shouldShowName() || entity.hasCustomName() && entity == this.entityRenderDispatcher.crosshairPickEntity);
    }

    @Override
    public ResourceLocation getTextureLocation(Dummy entity) {
        return Mannequins.path("textures/block/mannequin.png");
    }
}
