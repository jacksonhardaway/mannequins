package gg.moonflower.mannequins.core.fabric;

import gg.moonflower.mannequins.core.MannequinsClient;
import net.fabricmc.api.ClientModInitializer;

public class MannequinsFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        MannequinsClient.init();
        MannequinsClient.postInit();
    }
}
