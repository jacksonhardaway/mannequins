package me.jaackson.mannequins.forge;

import me.jaackson.mannequins.bridge.forge.RegistryBridgeImpl;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class MannequinsRegistryImpl {

    public static void register() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();

        RegistryBridgeImpl.ENTITIES.register(bus);
        RegistryBridgeImpl.ITEMS.register(bus);
        RegistryBridgeImpl.SOUND_EVENTS.register(bus);
    }

}
