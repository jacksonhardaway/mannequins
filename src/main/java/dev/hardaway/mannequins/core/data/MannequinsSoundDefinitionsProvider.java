package dev.hardaway.mannequins.core.data;

import dev.hardaway.mannequins.core.Mannequins;
import dev.hardaway.mannequins.core.registry.MannequinsSounds;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.common.data.SoundDefinition;
import net.neoforged.neoforge.common.data.SoundDefinitionsProvider;

public class MannequinsSoundDefinitionsProvider extends SoundDefinitionsProvider {

    public MannequinsSoundDefinitionsProvider(PackOutput output, ExistingFileHelper helper) {
        super(output, Mannequins.MOD_ID, helper);
    }

    @Override
    public void registerSounds() {
        this.add(MannequinsSounds.BLOCK_MANNEQUIN_BREAK, SoundDefinition.definition().subtitle("subtitles.block.generic.break")
                .with(
                        sound("entity/armorstand/break1"),
                        sound("entity/armorstand/break2"),
                        sound("entity/armorstand/break3"),
                        sound("entity/armorstand/break4")
                ));
        this.add(MannequinsSounds.BLOCK_MANNEQUIN_PLACE, SoundDefinition.definition().subtitle("subtitles.block.generic.place")
                .with(
                        sound("dig/wood1"),
                        sound("dig/wood2"),
                        sound("dig/wood3"),
                        sound("dig/wood4")
                ));
        this.add(MannequinsSounds.BLOCK_MANNEQUIN_HIT, SoundDefinition.definition().subtitle("subtitles.block.generic.hit")
                .with(
                        sound("entity/armorstand/hit1"),
                        sound("entity/armorstand/hit2"),
                        sound("entity/armorstand/hit3"),
                        sound("entity/armorstand/hit4")
                ));

        this.add(MannequinsSounds.BLOCK_STATUE_BREAK, SoundDefinition.definition().subtitle("subtitles.block.generic.break")
                .with(
                        sound("dig/stone1"),
                        sound("dig/stone2"),
                        sound("dig/stone3"),
                        sound("dig/stone4")
                ));
        this.add(MannequinsSounds.BLOCK_STATUE_PLACE, SoundDefinition.definition().subtitle("subtitles.block.generic.place")
                .with(
                        sound("dig/stone1"),
                        sound("dig/stone2"),
                        sound("dig/stone3"),
                        sound("dig/stone4")
                ));
        this.add(MannequinsSounds.BLOCK_STATUE_HIT, SoundDefinition.definition().subtitle("subtitles.block.generic.hit")
                .with(
                        sound("dig/stone1"),
                        sound("dig/stone2"),
                        sound("dig/stone3"),
                        sound("dig/stone4")
                ));
    }
}
