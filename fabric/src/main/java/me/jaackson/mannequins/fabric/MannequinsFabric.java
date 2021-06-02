package me.jaackson.mannequins.fabric;

import me.jaackson.mannequins.Mannequins;
import net.fabricmc.api.ModInitializer;

public class MannequinsFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        Mannequins.load();
    }

}
