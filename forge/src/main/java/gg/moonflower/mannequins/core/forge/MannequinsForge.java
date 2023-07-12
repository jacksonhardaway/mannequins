package gg.moonflower.mannequins.core.forge;

import dev.architectury.platform.forge.EventBuses;
import gg.moonflower.mannequins.core.Mannequins;
import gg.moonflower.mannequins.core.MannequinsClient;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Mannequins.MOD_ID)
public class MannequinsForge {
    public MannequinsForge() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        EventBuses.registerModEventBus(Mannequins.MOD_ID, eventBus);
        eventBus.addListener(this::commonInit);
        eventBus.addListener(this::clientInit);

        Mannequins.init();
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> MannequinsClient::init);
    }

    private void commonInit(FMLCommonSetupEvent event) {
        Mannequins.postInit();
    }

    private void clientInit(FMLClientSetupEvent event) {
        MannequinsClient.postInit();
    }
}
