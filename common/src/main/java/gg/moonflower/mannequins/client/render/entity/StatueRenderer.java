package gg.moonflower.mannequins.client.render.entity;

import gg.moonflower.mannequins.client.render.model.StatueModel;
import gg.moonflower.mannequins.common.entity.Statue;
import gg.moonflower.mannequins.core.Mannequins;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.resources.ResourceLocation;

public class StatueRenderer extends AbstractMannequinRenderer<Statue> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(Mannequins.MOD_ID, "textures/entity/statue.png");

    public StatueRenderer(EntityRenderDispatcher dispatcher) {
        super(dispatcher, new StatueModel());
    }

    @Override
    public ResourceLocation getTextureLocation(Statue entity) {
        return TEXTURE;
    }
}
