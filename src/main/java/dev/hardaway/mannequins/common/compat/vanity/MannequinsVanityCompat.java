package dev.hardaway.mannequins.common.compat.vanity;

import com.google.common.base.Suppliers;
import net.neoforged.fml.ModList;

import java.util.function.Supplier;

public class MannequinsVanityCompat {

    private static final Supplier<Boolean> ACTIVE = Suppliers.memoize(() -> ModList.get().isLoaded("vanity"));

    public static boolean isActive() {
        return ACTIVE.get();
    }
}
