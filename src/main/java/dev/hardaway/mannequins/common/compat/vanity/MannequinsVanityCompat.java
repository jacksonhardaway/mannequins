package dev.hardaway.mannequins.common.compat.vanity;

import com.google.common.base.Suppliers;
import net.neoforged.fml.ModList;

import java.util.function.Supplier;

public interface MannequinsVanityCompat {

    Supplier<Boolean> IS_LOADED = Suppliers.memoize(() -> ModList.get().isLoaded("vanity"));

    static boolean isLoaded() {
        return IS_LOADED.get();
    }
}
