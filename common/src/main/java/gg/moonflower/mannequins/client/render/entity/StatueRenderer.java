package gg.moonflower.mannequins.client.render.entity;

import gg.moonflower.mannequins.client.render.model.StatueModel;
import gg.moonflower.mannequins.common.entity.AbstractMannequin;
import gg.moonflower.mannequins.common.entity.Statue;
import gg.moonflower.mannequins.core.Mannequins;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.resources.ResourceLocation;

public class StatueRenderer extends AbstractMannequinRenderer<Statue> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(Mannequins.MOD_ID, "textures/entity/statue/statue.png");
    private static final ResourceLocation TROLLED = new ResourceLocation(Mannequins.MOD_ID, "textures/entity/statue/statue_trolled.png");
    private static final ResourceLocation[] EXPRESSIONS = new ResourceLocation[]{
            new ResourceLocation(Mannequins.MOD_ID, "textures/entity/statue/statue_neutral.png"),
            new ResourceLocation(Mannequins.MOD_ID, "textures/entity/statue/statue_happy.png"),
            new ResourceLocation(Mannequins.MOD_ID, "textures/entity/statue/statue_surprised.png"),
            new ResourceLocation(Mannequins.MOD_ID, "textures/entity/statue/statue_upset.png")
    };

    public StatueRenderer(EntityRenderDispatcher dispatcher) {
        super(dispatcher, new StatueModel());
    }

    @Override
    public ResourceLocation getMannequinTexture(Statue entity) {
        return TEXTURE;
    }

    @Override
    public ResourceLocation getMannequinExpressionTexture(Statue entity, AbstractMannequin.Expression expression) {
        if (entity.isTrolled())
            return TROLLED;
        return EXPRESSIONS[expression.ordinal() % EXPRESSIONS.length];
    }
}
