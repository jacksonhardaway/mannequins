package dev.hardaway.mannequins.core.registry;

import dev.hardaway.mannequins.common.block.entity.DummyBlockEntity;
import dev.hardaway.mannequins.common.item.MannequinItem;
import dev.hardaway.mannequins.core.Mannequins;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class MannequinsItems {

    public static final DeferredRegister.Items REGISTRY = DeferredRegister.createItems(Mannequins.MOD_ID);

    public static final DeferredItem<MannequinItem> MANNEQUIN = REGISTRY.registerItem("mannequin", p -> new MannequinItem(MannequinsBlocks.MANNEQUIN.get(),
            p
                    .component(MannequinsComponents.POSE, DummyBlockEntity.DEFAULT_POSE)
    ));

    public static final DeferredItem<MannequinItem> STATUE = REGISTRY.registerItem("statue", p -> new MannequinItem(MannequinsBlocks.STATUE.get(),
            p
                    .component(MannequinsComponents.POSE, DummyBlockEntity.DEFAULT_POSE)
    ));
}
