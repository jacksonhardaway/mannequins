package gg.moonflower.mannequins.client.render.entity;

import gg.moonflower.mannequins.client.render.model.BasicMannequinModel;
import gg.moonflower.mannequins.client.render.model.MannequinsModelLayers;
import gg.moonflower.mannequins.client.render.model.StatueModel;
import gg.moonflower.mannequins.common.entity.Statue;
import gg.moonflower.mannequins.core.Mannequins;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class StatueRenderer extends AbstractMannequinRenderer<Statue> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(Mannequins.MOD_ID, "textures/entity/statue.png");

    public StatueRenderer(EntityRendererProvider.Context context) {
        super(context, new StatueModel(context.bakeLayer(MannequinsModelLayers.STATUE)), new BasicMannequinModel<>(context.bakeLayer(MannequinsModelLayers.STATUE_INNER_ARMOR)), new BasicMannequinModel<>(context.bakeLayer(MannequinsModelLayers.STATUE_OUTER_ARMOR)));
    }

    @Override
    public ResourceLocation getTextureLocation(Statue entity) {
        return TEXTURE;
    }
}
