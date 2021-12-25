package gg.moonflower.mannequins.core.forge;

import gg.moonflower.mannequins.core.Mannequins;
import net.minecraftforge.fml.common.Mod;

/**
 * @author Jackson
 */
@Mod(Mannequins.MOD_ID)
public class MannequinsForge {
    public MannequinsForge() {
        Mannequins.PLATFORM.setup();
    }
}
