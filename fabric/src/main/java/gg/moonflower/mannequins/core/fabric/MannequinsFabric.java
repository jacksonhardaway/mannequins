package gg.moonflower.mannequins.core.fabric;

import gg.moonflower.mannequins.common.entity.Mannequin;
import gg.moonflower.mannequins.core.Mannequins;
import gg.moonflower.mannequins.core.MannequinsRegistry;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.minecraft.world.entity.ai.attributes.Attributes;

/**
 * @author Jackson
 */
public class MannequinsFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        Mannequins.PLATFORM.setup();
        FabricDefaultAttributeRegistry.register(MannequinsRegistry.MANNEQUIN.get(), Mannequin.createLivingAttributes().add(Attributes.KNOCKBACK_RESISTANCE, 1.0));
    }
}
