package gg.moonflower.mannequins.core.fabric;

import gg.moonflower.mannequins.core.Mannequins;
import net.fabricmc.api.ModInitializer;

/**
 * @author Jackson
 */
public class MannequinsFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        Mannequins.init();
    }
}
