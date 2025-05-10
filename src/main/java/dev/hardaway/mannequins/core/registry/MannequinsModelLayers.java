package dev.hardaway.mannequins.core.registry;

import dev.hardaway.mannequins.client.model.DummyModel;
import dev.hardaway.mannequins.client.model.MannequinModel;
import dev.hardaway.mannequins.client.model.StatueModel;
import dev.hardaway.mannequins.core.Mannequins;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

public class MannequinsModelLayers {
    public static final ModelLayerLocation MANNEQUIN = create("mannequin");
    public static final ModelLayerLocation STATUE = create("statue");
    public static final ModelLayerLocation DUMMY_INNER_ARMOR = create("mannequin", "inner_armor");
    public static final ModelLayerLocation DUMMY_OUTER_ARMOR = create("mannequin", "outer_armor");

    public static final ModelLayerLocation STATUE_INNER_ARMOR = create("statue", "inner_armor");
    public static final ModelLayerLocation STATUE_OUTER_ARMOR = create("statue", "outer_armor");


    public static ModelLayerLocation create(String model) {
        return create(model, "main");
    }

    public static ModelLayerLocation create(String model, String layer) {
        return new ModelLayerLocation(Mannequins.path(model), layer);
    }

    @SubscribeEvent
    public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(MannequinsModelLayers.MANNEQUIN, MannequinModel::createBodyLayer);
        event.registerLayerDefinition(MannequinsModelLayers.STATUE, StatueModel::createBodyLayer);
        event.registerLayerDefinition(MannequinsModelLayers.DUMMY_INNER_ARMOR, () -> MannequinModel.createBodyLayer(new CubeDeformation(0.5F)));
        event.registerLayerDefinition(MannequinsModelLayers.DUMMY_OUTER_ARMOR, () -> MannequinModel.createBodyLayer(new CubeDeformation(1.0F)));
    }
}
