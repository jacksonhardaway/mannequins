package dev.hardaway.mannequins.common.block;

import com.mojang.serialization.MapCodec;
import dev.hardaway.mannequins.common.block.entity.DummyBlockEntity;
import dev.hardaway.mannequins.common.menu.DummyEditorMenu;
import dev.hardaway.mannequins.core.Mannequins;
import dev.hardaway.mannequins.core.registry.MannequinsMenus;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.block.BaseEntityBlock;
import org.jetbrains.annotations.Nullable;

public class MannequinBlock extends DummyBlock {
    public static final MapCodec<DummyBlock> CODEC = simpleCodec(MannequinBlock::new);
    public static final ResourceLocation MANNEQUIN_TEXTURE = Mannequins.path("textures/block/mannequin.png");

    public MannequinBlock(Properties properties) {
        super(properties);
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int id, Inventory inventory, ContainerLevelAccess access, DummyBlockEntity dummy) {
        return new DummyEditorMenu(MannequinsMenus.MANNEQUIN, id, inventory, access, dummy);
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Override
    public ResourceLocation getDummyTexture() {
        return MANNEQUIN_TEXTURE;
    }
}
