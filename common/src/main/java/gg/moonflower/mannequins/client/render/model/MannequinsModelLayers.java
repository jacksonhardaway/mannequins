package gg.moonflower.mannequins.client.render.model;

import gg.moonflower.mannequins.core.Mannequins;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

public class MannequinsModelLayers {
    public static final ModelLayerLocation MANNEQUIN = create("mannequin");
    public static final ModelLayerLocation MANNEQUIN_INNER_ARMOR = create("mannequin", "inner_armor");
    public static final ModelLayerLocation MANNEQUIN_OUTER_ARMOR = create("mannequin", "outer_armor");

    public static final ModelLayerLocation STATUE = create("statue");
    public static final ModelLayerLocation STATUE_INNER_ARMOR = create("statue", "inner_armor");
    public static final ModelLayerLocation STATUE_OUTER_ARMOR = create("statue", "outer_armor");

    public static ModelLayerLocation create(String model) {
        return create(model, "main");
    }

    public static ModelLayerLocation create(String model, String layer) {
        return new ModelLayerLocation(new ResourceLocation(Mannequins.MOD_ID, model), layer);
    }
}
