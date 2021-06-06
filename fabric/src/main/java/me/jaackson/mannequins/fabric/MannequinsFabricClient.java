package me.jaackson.mannequins.fabric;

import me.jaackson.mannequins.Mannequins;
import net.fabricmc.api.ClientModInitializer;

/**
 * @author Jackson
 */
public class MannequinsFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        Mannequins.clientInit();
        Mannequins.clientSetup();
    }
}
