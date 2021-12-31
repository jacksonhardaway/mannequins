package gg.moonflower.mannequins.client.render.entity;

import gg.moonflower.mannequins.client.render.model.BasicMannequinModel;
import gg.moonflower.mannequins.client.render.model.MannequinModel;
import gg.moonflower.mannequins.client.render.model.MannequinsModelLayers;
import gg.moonflower.mannequins.client.render.model.StoneMannequinModel;
import gg.moonflower.mannequins.common.entity.StoneMannequin;
import gg.moonflower.mannequins.core.Mannequins;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class StoneMannequinRenderer extends AbstractMannequinRenderer<StoneMannequin> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(Mannequins.MOD_ID, "textures/entity/stone_mannequin.png");

    public StoneMannequinRenderer(EntityRendererProvider.Context context) {
        super(context, new StoneMannequinModel(context.bakeLayer(MannequinsModelLayers.STONE_MANNEQUIN)), new BasicMannequinModel<>(context.bakeLayer(MannequinsModelLayers.STONE_MANNEQUIN_INNER_ARMOR)), new BasicMannequinModel<>(context.bakeLayer(MannequinsModelLayers.STONE_MANNEQUIN_OUTER_ARMOR)));
    }

    @Override
    public ResourceLocation getTextureLocation(StoneMannequin entity) {
        return TEXTURE;
    }
}
