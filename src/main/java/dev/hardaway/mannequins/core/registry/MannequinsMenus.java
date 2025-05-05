package dev.hardaway.mannequins.core.registry;

import dev.hardaway.mannequins.common.menu.MannequinMenu;
import dev.hardaway.mannequins.core.Mannequins;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class MannequinsMenus {

    public static final DeferredRegister<MenuType<?>> REGISTRY = DeferredRegister.create(Registries.MENU, Mannequins.MOD_ID);

    public static final Supplier<MenuType<MannequinMenu>> MANNEQUIN = REGISTRY.register("mannequin", () -> IMenuTypeExtension.create(MannequinMenu::new));
}
