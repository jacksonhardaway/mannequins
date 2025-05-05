package dev.hardaway.mannequins.core.data;

import dev.hardaway.mannequins.core.Mannequins;
import dev.hardaway.mannequins.core.registry.MannequinsItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class MannequinsItemModelProvider extends ItemModelProvider {
    public MannequinsItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Mannequins.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        this.basicItem(MannequinsItems.MANNEQUIN.get());
    }
}
