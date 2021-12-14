package gg.moonflower.mannequins.client.render.model.fabric;

import gg.moonflower.mannequins.core.Mannequins;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Supplier;

public class MannequinsModelLayersImpl {

    public static ModelLayerLocation register(String model, String layer, Supplier<LayerDefinition> definition) {
        ModelLayerLocation location =  new ModelLayerLocation(new ResourceLocation(Mannequins.MOD_ID, model), layer);
        EntityModelLayerRegistry.registerModelLayer(location, definition::get);
        return location;
    }

}
