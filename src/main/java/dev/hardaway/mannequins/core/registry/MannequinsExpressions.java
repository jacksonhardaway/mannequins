package dev.hardaway.mannequins.core.registry;

import dev.hardaway.mannequins.api.DummyExpression;
import dev.hardaway.mannequins.core.Mannequins;
import net.minecraft.resources.ResourceKey;
import net.neoforged.neoforge.registries.DeferredRegister;

public class MannequinsExpressions {

    public static final DeferredRegister<DummyExpression> REGISTRY = DeferredRegister.create(MannequinsRegistries.MANNEQUIN_EXPRESSIONS, Mannequins.MOD_ID);

    public static final ResourceKey<DummyExpression> TROLLED = ResourceKey.create(MannequinsRegistries.MANNEQUIN_EXPRESSIONS, Mannequins.path("editor/trolled"));
}
