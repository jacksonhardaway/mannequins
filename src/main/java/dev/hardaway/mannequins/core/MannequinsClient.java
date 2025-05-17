package dev.hardaway.mannequins.core;

import dev.hardaway.mannequins.client.block.MannequinClientBlockExtension;
import dev.hardaway.mannequins.client.model.DummyModel;
import dev.hardaway.mannequins.client.model.MannequinModel;
import dev.hardaway.mannequins.client.model.StatueModel;
import dev.hardaway.mannequins.client.render.block.DummyBlockEntityRenderer;
import dev.hardaway.mannequins.client.render.entity.DummyEntityRenderer;
import dev.hardaway.mannequins.client.screen.MannequinEditorScreen;
import dev.hardaway.mannequins.client.screen.StatueEditorScreen;
import dev.hardaway.mannequins.core.registry.*;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.HitResult;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;

import java.util.function.Function;

@Mod(value = Mannequins.MOD_ID, dist = Dist.CLIENT)
public class MannequinsClient {

    public MannequinsClient(IEventBus bus) {
        bus.addListener(this::registerRenderers);
        bus.addListener(this::registerMenuScreens);
        bus.addListener(this::registerClientExtensions);
        bus.register(MannequinsModelLayers.class);
    }

    private void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(MannequinsEntities.DUMMY.get(), context -> new DummyEntityRenderer(context,
                new MannequinModel(context.bakeLayer(MannequinsModelLayers.MANNEQUIN)),
                new DummyModel(context.bakeLayer(MannequinsModelLayers.DUMMY_INNER_ARMOR)),
                new DummyModel(context.bakeLayer(MannequinsModelLayers.DUMMY_OUTER_ARMOR))
        ));
        event.registerBlockEntityRenderer(MannequinsBlockEntities.MANNEQUIN.get(), DummyBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(MannequinsBlockEntities.STATUE.get(), DummyBlockEntityRenderer::new);
    }

    private void registerMenuScreens(RegisterMenuScreensEvent event) {
        event.register(MannequinsMenus.MANNEQUIN.get(), MannequinEditorScreen::new);
        event.register(MannequinsMenus.STATUE.get(), StatueEditorScreen::new);
    }

    private void registerClientExtensions(RegisterClientExtensionsEvent event) {
        event.registerBlock(new MannequinClientBlockExtension() {
            @Override
            public Function<EntityRendererProvider.Context, DummyModel> getRenderingDelegate() {
                return context -> new MannequinModel(context.bakeLayer(MannequinsModelLayers.MANNEQUIN));
            }

            @Override
            public boolean addHitEffects(BlockState state, Level level, HitResult target, ParticleEngine manager) {
                return true;
            }

            @Override
            public boolean addDestroyEffects(BlockState state, Level Level, BlockPos pos, ParticleEngine manager) {
                return false;
            }
        }, MannequinsBlocks.MANNEQUIN);

        event.registerBlock((MannequinClientBlockExtension) () -> context -> new StatueModel(context.bakeLayer(MannequinsModelLayers.STATUE)), MannequinsBlocks.STATUE);
    }
}
