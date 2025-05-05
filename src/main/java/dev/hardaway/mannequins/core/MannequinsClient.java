package dev.hardaway.mannequins.core;

import dev.hardaway.mannequins.client.model.DummyModel;
import dev.hardaway.mannequins.client.model.MannequinModel;
import dev.hardaway.mannequins.client.model.MannequinsModelLayers;
import dev.hardaway.mannequins.client.render.block.MannequinBlockEntityRenderer;
import dev.hardaway.mannequins.client.render.entity.DummyEntityRenderer;
import dev.hardaway.mannequins.client.screen.MannequinScreen;
import dev.hardaway.mannequins.core.registry.MannequinsBlockEntities;
import dev.hardaway.mannequins.core.registry.MannequinsEntities;
import dev.hardaway.mannequins.core.registry.MannequinsMenus;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

@Mod(value = Mannequins.MOD_ID, dist = Dist.CLIENT)
public class MannequinsClient {

    public MannequinsClient(IEventBus bus) {
        bus.addListener(this::registerLayerDefinitions);
        bus.addListener(this::registerRenderers);
        bus.addListener(this::registerMenuScreens);
    }

    private void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(MannequinsModelLayers.MANNEQUIN, MannequinModel::createMesh);
        event.registerLayerDefinition(MannequinsModelLayers.MANNEQUIN_INNER_ARMOR, () -> MannequinModel.createMesh(new CubeDeformation(0.5F)));
        event.registerLayerDefinition(MannequinsModelLayers.MANNEQUIN_OUTER_ARMOR, () -> MannequinModel.createMesh(new CubeDeformation(1.0F)));
    }

    private void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(MannequinsEntities.DUMMY.get(), context -> new DummyEntityRenderer(context,
                new MannequinModel(context.bakeLayer(MannequinsModelLayers.MANNEQUIN)),
                new DummyModel(context.bakeLayer(MannequinsModelLayers.MANNEQUIN_INNER_ARMOR)),
                new DummyModel(context.bakeLayer(MannequinsModelLayers.MANNEQUIN_OUTER_ARMOR))
        ));
        event.registerBlockEntityRenderer(MannequinsBlockEntities.MANNEQUIN.get(), MannequinBlockEntityRenderer::new);
    }

    private void registerMenuScreens(RegisterMenuScreensEvent event) {
        event.register(MannequinsMenus.MANNEQUIN.get(), MannequinScreen::new);
    }
}
