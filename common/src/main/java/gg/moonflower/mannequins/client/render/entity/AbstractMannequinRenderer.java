package gg.moonflower.mannequins.client.render.entity;

import gg.moonflower.mannequins.client.render.model.BasicMannequinModel;
import gg.moonflower.mannequins.common.entity.AbstractMannequin;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.client.renderer.entity.layers.ElytraLayer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;

/**
 * @author Ocelot, Jackson
 */
public abstract class AbstractMannequinRenderer<T extends AbstractMannequin> extends LivingEntityRenderer<T, BasicMannequinModel<T>> {

    public AbstractMannequinRenderer(EntityRenderDispatcher dispatcher, BasicMannequinModel<T> model) {
        super(dispatcher, model, 0.0F);
        this.addLayer(new HumanoidArmorLayer<>(this, new BasicMannequinModel<>(0.5F), new BasicMannequinModel<>(1.0F)));
        this.addLayer(new ElytraLayer<>(this));
        this.addLayer(new CustomHeadLayer<>(this));
        this.addLayer(new ItemInHandLayer<>(this));
    }

    @Override
    protected boolean shouldShowName(T entity) {
        return super.shouldShowName(entity) && (entity.shouldShowName() || entity.hasCustomName() && entity == this.entityRenderDispatcher.crosshairPickEntity);
    }
}