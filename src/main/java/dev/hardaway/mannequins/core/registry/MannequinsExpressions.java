package dev.hardaway.mannequins.core.registry;

import dev.hardaway.mannequins.api.DummyExpression;
import dev.hardaway.mannequins.core.Mannequins;
import net.minecraft.resources.ResourceKey;

public class MannequinsExpressions {

    public static final ResourceKey<DummyExpression> MANNEQUIN_TROLLED = ResourceKey.create(MannequinsRegistries.EXPRESSIONS, Mannequins.path("mannequin/trolled"));
    public static final ResourceKey<DummyExpression> MANNEQUIN_HAPPY = ResourceKey.create(MannequinsRegistries.EXPRESSIONS, Mannequins.path("mannequin/happy"));
    public static final ResourceKey<DummyExpression> MANNEQUIN_NEUTRAL = ResourceKey.create(MannequinsRegistries.EXPRESSIONS, Mannequins.path("mannequin/neutral"));
    public static final ResourceKey<DummyExpression> MANNEQUIN_SURPRISED = ResourceKey.create(MannequinsRegistries.EXPRESSIONS, Mannequins.path("mannequin/surprised"));
    public static final ResourceKey<DummyExpression> MANNEQUIN_UPSET = ResourceKey.create(MannequinsRegistries.EXPRESSIONS, Mannequins.path("mannequin/upset"));

    public static final ResourceKey<DummyExpression> STATUE_TROLLED = ResourceKey.create(MannequinsRegistries.EXPRESSIONS, Mannequins.path("statue/trolled"));
    public static final ResourceKey<DummyExpression> STATUE_HAPPY = ResourceKey.create(MannequinsRegistries.EXPRESSIONS, Mannequins.path("statue/happy"));
    public static final ResourceKey<DummyExpression> STATUE_NEUTRAL = ResourceKey.create(MannequinsRegistries.EXPRESSIONS, Mannequins.path("statue/neutral"));
    public static final ResourceKey<DummyExpression> STATUE_SURPRISED = ResourceKey.create(MannequinsRegistries.EXPRESSIONS, Mannequins.path("statue/surprised"));
    public static final ResourceKey<DummyExpression> STATUE_UPSET = ResourceKey.create(MannequinsRegistries.EXPRESSIONS, Mannequins.path("statue/upset"));
}
