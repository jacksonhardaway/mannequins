package dev.hardaway.mannequins.core.data;

import dev.hardaway.mannequins.core.Mannequins;
import dev.hardaway.mannequins.core.registry.MannequinsBlocks;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.client.model.generators.ConfiguredModel;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class MannequinsBlockStateProvider extends BlockStateProvider {
    public MannequinsBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, Mannequins.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        // TODO: rotation
//        this.getVariantBuilder(MannequinsBlocks.MANNEQUIN.get())
//                .partialState().setModels(new ConfiguredModel(this.models().getExistingFile(Mannequins.path("mannequin"))));
    }
}
