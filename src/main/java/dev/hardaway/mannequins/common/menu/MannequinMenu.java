package dev.hardaway.mannequins.common.menu;

import com.mojang.datafixers.util.Pair;
import dev.hardaway.mannequins.common.block.entity.MannequinBlockEntity;
import dev.hardaway.mannequins.common.component.MannequinPose;
import dev.hardaway.mannequins.core.Mannequins;
import dev.hardaway.mannequins.core.registry.MannequinsBlocks;
import dev.hardaway.mannequins.core.registry.MannequinsMenus;
import dev.hardaway.mannequins.core.util.QuickMoveHelper;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.items.ItemStackHandler;
import net.neoforged.neoforge.items.SlotItemHandler;

public class MannequinMenu extends AbstractContainerMenu {
    public static final ResourceLocation EMPTY_MANNEQUIN_SLOT_MAINHAND = Mannequins.path("item/empty_mannequin_slot_mainhand");
    private static final QuickMoveHelper MOVE_HELPER = new QuickMoveHelper().
            add(0, 4, 4, 36, true). // Mannequin to Inventory
                    add(4, 36, 0, 4, false); // Inventory to Mannequin

    private final MannequinInventory mannequinInventory;
    private final ContainerLevelAccess access;
    private final MannequinPose mannequinPose;

    public MannequinMenu(int containerId, Inventory playerInventory, FriendlyByteBuf data) {
        this(containerId, playerInventory, new MannequinInventory(), ContainerLevelAccess.NULL, MannequinPose.STREAM_CODEC.decode(data));
    }

    public MannequinMenu(int id, Inventory inventory, MannequinInventory mannequinInventory, ContainerLevelAccess access, MannequinPose pose) {
        super(MannequinsMenus.MANNEQUIN.get(), id);
        this.access = access;
        this.mannequinPose = pose;
        this.mannequinInventory = mannequinInventory;

        this.addSlot(new SlotItemHandler(mannequinInventory, 0, 8, 10 + 8) {
            @Override
            public int getMaxStackSize() {
                return 1;
            }


            @Override
            public boolean mayPlace(ItemStack itemStack) {
                return inventory.player.getEquipmentSlotForItem(itemStack) == EquipmentSlot.HEAD;
            }

            @Override
            public Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
                return Pair.of(InventoryMenu.BLOCK_ATLAS, InventoryMenu.EMPTY_ARMOR_SLOT_HELMET);
            }
        });
        this.addSlot(new SlotItemHandler(mannequinInventory, 1, 8, 10 + 8 + 18) {
            @Override
            public int getMaxStackSize() {
                return 1;
            }

            @Override
            public boolean mayPlace(ItemStack itemStack) {
                return inventory.player.getEquipmentSlotForItem(itemStack) == EquipmentSlot.CHEST;
            }

            @Override
            public Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
                return Pair.of(InventoryMenu.BLOCK_ATLAS, InventoryMenu.EMPTY_ARMOR_SLOT_CHESTPLATE);
            }
        });

        this.addSlot(new SlotItemHandler(mannequinInventory, 2, 8, 10 + 8 + 2 * 18) {
            @Override
            public Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
                return Pair.of(InventoryMenu.BLOCK_ATLAS, EMPTY_MANNEQUIN_SLOT_MAINHAND);
            }
        });
        this.addSlot(new SlotItemHandler(mannequinInventory, 3, 8, 10 + 8 + 3 * 18) {
            @Override
            public Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
                return Pair.of(InventoryMenu.BLOCK_ATLAS, InventoryMenu.EMPTY_ARMOR_SLOT_SHIELD);
            }
        });

        for (int y = 0; y < 3; ++y) {
            for (int x = 0; x < 9; ++x) {
                this.addSlot(new Slot(inventory, x + y * 9 + 9, 8 + x * 18, 19 + 102 + y * 18 - 18));
            }
        }

        for (int x = 0; x < 9; ++x) {
            this.addSlot(new Slot(inventory, x, 8 + x * 18, 19 + 142));
        }
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(this.access, player, MannequinsBlocks.MANNEQUIN.get());
    }

    @Override
    public ItemStack quickMoveStack(Player player, int slot) {
        return MOVE_HELPER.quickMoveStack(this, player, slot);
    }

    public MannequinInventory getMannequinInventory() {
        return mannequinInventory;
    }

    public MannequinPose getMannequinPose() {
        return mannequinPose;
    }

    public void setMannequinPose(MannequinPose pose) {
        this.access.execute((level, pos) -> {
            BlockEntity be = level.getBlockEntity(pos);
            if (be instanceof MannequinBlockEntity mannequin) {
                mannequin.setPose(pose);
                level.sendBlockUpdated(pos, mannequin.getBlockState(), mannequin.getBlockState(), 3);
            }
        });
    }
}
