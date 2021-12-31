package gg.moonflower.mannequins.client.render.entity;

import gg.moonflower.mannequins.client.render.model.StoneMannequinModel;
import gg.moonflower.mannequins.common.entity.StoneMannequin;
import gg.moonflower.mannequins.core.Mannequins;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.resources.ResourceLocation;

public class StoneMannequinRenderer extends AbstractMannequinRenderer<StoneMannequin> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(Mannequins.MOD_ID, "textures/entity/stone_mannequin.png");

    public StoneMannequinRenderer(EntityRenderDispatcher dispatcher) {
        super(dispatcher, new StoneMannequinModel());
    }

    @Override
    public ResourceLocation getTextureLocation(StoneMannequin entity) {
        return TEXTURE;
    }
}
