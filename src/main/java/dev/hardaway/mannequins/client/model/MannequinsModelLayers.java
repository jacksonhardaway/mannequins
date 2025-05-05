package dev.hardaway.mannequins.client.model;

import dev.hardaway.mannequins.core.Mannequins;
import net.minecraft.client.model.geom.ModelLayerLocation;

public class MannequinsModelLayers {
    public static final ModelLayerLocation MANNEQUIN = create("mannequin");
    public static final ModelLayerLocation MANNEQUIN_INNER_ARMOR = create("mannequin", "inner_armor");
    public static final ModelLayerLocation MANNEQUIN_OUTER_ARMOR = create("mannequin", "outer_armor");

    public static ModelLayerLocation create(String model) {
        return create(model, "main");
    }

    public static ModelLayerLocation create(String model, String layer) {
        return new ModelLayerLocation(Mannequins.path(model), layer);
    }
}
