package gg.moonflower.mannequins.client.render.model;

import dev.architectury.injectables.annotations.ExpectPlatform;
import gg.moonflower.pollen.api.platform.Platform;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;

import java.util.function.Supplier;

public class MannequinsModelLayers {
    private static final CubeDeformation OUTER_ARMOR_DEFORMATION = new CubeDeformation(1.0F);
    private static final CubeDeformation INNER_ARMOR_DEFORMATION = new CubeDeformation(0.5F);

    public static final ModelLayerLocation MANNEQUIN = register("mannequin", "main", MannequinFullModel::createLayerDefinition);
    public static final ModelLayerLocation MANNEQUIN_INNER_ARMOR = registerInnerArmor("mannequin", () -> MannequinModel.createLayerDefinition(INNER_ARMOR_DEFORMATION));
    public static final ModelLayerLocation MANNEQUIN_OUTER_ARMOR = registerOuterArmor("mannequin", () -> MannequinModel.createLayerDefinition(OUTER_ARMOR_DEFORMATION));

    private static ModelLayerLocation registerInnerArmor(String string, Supplier<LayerDefinition> definition) {
        return register(string, "inner_armor", definition);
    }

    private static ModelLayerLocation registerOuterArmor(String string, Supplier<LayerDefinition> definition) {
        return register(string, "outer_armor", definition);
    }

    @ExpectPlatform
    public static ModelLayerLocation register(String model, String layer, Supplier<LayerDefinition> definition) {
        return Platform.error();
    }

    public static void init() {}
}
