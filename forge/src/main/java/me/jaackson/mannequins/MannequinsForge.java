package me.jaackson.mannequins;

import me.jaackson.mannequins.bridge.forge.RegistryBridgeImpl;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

/**
 * @author Jackson
 */
@Mod(Mannequins.MOD_ID)
public class MannequinsForge {

    public MannequinsForge() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(this::commonSetup);
        bus.addListener(this::clientSetup);
        Mannequins.init();

        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> Mannequins::clientInit);

        RegistryBridgeImpl.ENTITIES.register(bus);
        RegistryBridgeImpl.ITEMS.register(bus);
        RegistryBridgeImpl.SOUND_EVENTS.register(bus);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(Mannequins::commonSetup);
    }

    private void clientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(Mannequins::clientSetup);
    }
}
