package dev.hardaway.mannequins.common.compat.vanity;

import com.mojang.datafixers.util.Pair;
import dev.hardaway.mannequins.api.MannequinTexture;
import net.minecraft.resources.ResourceLocation;
import tech.thatgravyboat.vanity.api.design.Design;

public class VanityMannequinTexture implements MannequinTexture {

    public VanityMannequinTexture(Design design) {

    }

    @Override
    public ResourceLocation getTexture() {
        return null;
    }
}
