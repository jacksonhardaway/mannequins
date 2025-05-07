package dev.hardaway.mannequins.common.compat.vanity;

import dev.hardaway.mannequins.core.Mannequins;
import net.minecraft.resources.ResourceLocation;
import tech.thatgravyboat.vanity.api.style.AssetType;
import tech.thatgravyboat.vanity.api.style.AssetTypes;

public class MannequinsAssetTypes {

    public static final AssetType MANNEQUIN = MannequinsAssetTypes.register("mannequin");

    private static AssetType register(String name) {
        return AssetTypes.register(Mannequins.MOD_ID + ":" + name);
    }
}
