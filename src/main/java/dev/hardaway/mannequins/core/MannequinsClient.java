package dev.hardaway.mannequins.core;

import dev.hardaway.mannequins.client.model.DummyModel;
import dev.hardaway.mannequins.client.model.MannequinModel;
import dev.hardaway.mannequins.core.registry.MannequinsModelLayers;
import dev.hardaway.mannequins.client.render.block.MannequinBlockEntityRenderer;
import dev.hardaway.mannequins.client.render.entity.DummyEntityRenderer;
import dev.hardaway.mannequins.client.screen.MannequinEditorScreen;
import dev.hardaway.mannequins.client.screen.StatueEditorScreen;
import dev.hardaway.mannequins.core.registry.MannequinsBlockEntities;
import dev.hardaway.mannequins.core.registry.MannequinsEntities;
import dev.hardaway.mannequins.core.registry.MannequinsMenus;
import net.minecraft.client.model.HumanoidModel;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

@Mod(value = Mannequins.MOD_ID, dist = Dist.CLIENT)
public class MannequinsClient {

    public MannequinsClient(IEventBus bus) {
        bus.addListener(this::registerRenderers);
        bus.addListener(this::registerMenuScreens);
        bus.register(MannequinsModelLayers.class);
    }

    private void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(MannequinsEntities.DUMMY.get(), context -> new DummyEntityRenderer(context,
                new MannequinModel(context.bakeLayer(MannequinsModelLayers.MANNEQUIN)),
                new DummyModel(context.bakeLayer(MannequinsModelLayers.DUMMY_INNER_ARMOR)),
                new DummyModel(context.bakeLayer(MannequinsModelLayers.DUMMY_OUTER_ARMOR))
        ));
        event.registerBlockEntityRenderer(MannequinsBlockEntities.DUMMY.get(), MannequinBlockEntityRenderer::new);
    }

    private void registerMenuScreens(RegisterMenuScreensEvent event) {
        event.register(MannequinsMenus.MANNEQUIN.get(), MannequinEditorScreen::new);
        event.register(MannequinsMenus.STATUE.get(), StatueEditorScreen::new);
    }
}
