package me.jaackson.mannequins.fabric;

import me.jaackson.mannequins.Mannequins;
import net.fabricmc.api.ModInitializer;

/**
 * @author Jackson
 */
public class MannequinsFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        Mannequins.commonInit();
        Mannequins.commonPostInit();
        Mannequins.commonNetworkingInit();
    }

}
