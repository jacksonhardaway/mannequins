package me.jaackson.mannequins;

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

        Mannequins.commonInit();
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> Mannequins::clientInit);

        Mannequins.commonNetworkingInit();
        Mannequins.clientNetworkingInit();


    }

    private void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(Mannequins::commonPostInit);
    }

    private void clientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(Mannequins::clientPostInit);
    }
}
