package gg.moonflower.mannequins.core.forge;

import gg.moonflower.mannequins.client.render.model.MannequinsModelLayers;
import gg.moonflower.mannequins.core.Mannequins;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

/**
 * @author Jackson
 */
@Mod(Mannequins.MOD_ID)
public class MannequinsForge {
    public MannequinsForge() {
        FMLJavaModLoadingContext.get().getModEventBus().<EntityRenderersEvent.RegisterLayerDefinitions>addListener(e -> MannequinsModelLayers.init());
        Mannequins.PLATFORM.setup();
    }
}
