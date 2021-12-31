package gg.moonflower.mannequins.client.render.model;

import gg.moonflower.mannequins.core.Mannequins;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

public class MannequinsModelLayers {
    public static final ModelLayerLocation MANNEQUIN = create("mannequin", "main");
    public static final ModelLayerLocation MANNEQUIN_INNER_ARMOR = create("mannequin", "inner_armor");
    public static final ModelLayerLocation MANNEQUIN_OUTER_ARMOR = create("mannequin", "outer_armor");

    public static final ModelLayerLocation STONE_MANNEQUIN = create("stone_mannequin", "main");
    public static final ModelLayerLocation STONE_MANNEQUIN_INNER_ARMOR = create("stone_mannequin", "inner_armor");
    public static final ModelLayerLocation STONE_MANNEQUIN_OUTER_ARMOR = create("stone_mannequin", "outer_armor");

    public static ModelLayerLocation create(String model) {
        return create(model, "main");
    }

    public static ModelLayerLocation create(String model, String layer) {
        return new ModelLayerLocation(new ResourceLocation(Mannequins.MOD_ID, model), layer);
    }
}
