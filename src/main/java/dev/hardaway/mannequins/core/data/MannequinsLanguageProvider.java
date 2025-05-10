package dev.hardaway.mannequins.core.data;

import dev.hardaway.mannequins.core.Mannequins;
import dev.hardaway.mannequins.core.registry.MannequinsBlocks;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public class MannequinsLanguageProvider extends LanguageProvider {
    public MannequinsLanguageProvider(PackOutput output) {
        super(output, Mannequins.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations() {
        this.addBlock(MannequinsBlocks.MANNEQUIN, "Mannequin");
        this.addBlock(MannequinsBlocks.STATUE, "Statue");
    }
}
