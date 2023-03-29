package gg.moonflower.mannequins.common.menu;

import com.mojang.datafixers.util.Pair;
import gg.moonflower.mannequins.common.entity.AbstractMannequin;
import gg.moonflower.mannequins.core.Mannequins;
import gg.moonflower.pollen.api.container.util.v1.QuickMoveHelper;
import net.minecraft.core.Rotations;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

/**
 * @author Ocelot, Jackson
 */
public class MannequinInventoryMenu extends AbstractContainerMenu {
    public static final ResourceLocation EMPTY_MANNEQUIN_SLOT_MAINHAND = new ResourceLocation(Mannequins.MOD_ID, "item/empty_mannequin_slot_mainhand");
    private static final QuickMoveHelper MOVE_HELPER = new QuickMoveHelper().
            add(0, 4, 4, 36, true). // Mannequin to Inventory
                    add(4, 36, 0, 4, false); // Inventory to Mannequin

    private final AbstractMannequin mannequin;

    public MannequinInventoryMenu(int id, Inventory inventory, Container container, AbstractMannequin mannequin) {
        super(null, id);
        this.mannequin = mannequin;

        this.addSlot(new Slot(container, 0, 8, 10 + 8) {
            @Override
            public int getMaxStackSize() {
                return 1;
            }

            @Override
            public boolean mayPlace(ItemStack itemStack) {
                return Mob.getEquipmentSlotForItem(itemStack) == EquipmentSlot.HEAD;
            }

            @Override
            public Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
                return Pair.of(InventoryMenu.BLOCK_ATLAS, InventoryMenu.EMPTY_ARMOR_SLOT_HELMET);
            }
        });
        this.addSlot(new Slot(container, 1, 8, 10 + 8 + 18) {
            @Override
            public int getMaxStackSize() {
                return 1;
            }

            @Override
            public boolean mayPlace(ItemStack itemStack) {
                return Mob.getEquipmentSlotForItem(itemStack) == EquipmentSlot.CHEST;
            }

            @Override
            public Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
                return Pair.of(InventoryMenu.BLOCK_ATLAS, InventoryMenu.EMPTY_ARMOR_SLOT_CHESTPLATE);
            }
        });

        this.addSlot(new Slot(container, 2, 8, 10 + 8 + 2 * 18) {
            @Override
            public Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
                return Pair.of(InventoryMenu.BLOCK_ATLAS, EMPTY_MANNEQUIN_SLOT_MAINHAND);
            }
        });
        this.addSlot(new Slot(container, 3, 8, 10 + 8 + 3 * 18) {
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

    public void setMannequinPose(Rotations headRotations, Rotations bodyRotations, Rotations leftArmRotations, Rotations rightArmRotations) {
        this.mannequin.setHeadPose(headRotations);
        this.mannequin.setBodyPose(bodyRotations);
        this.mannequin.setLeftArmPose(leftArmRotations);
        this.mannequin.setRightArmPose(rightArmRotations);
    }

    @Override
    public boolean stillValid(Player player) {
        return this.mannequin.isAlive() && !this.mannequin.isDisabled() && player.distanceToSqr(this.mannequin) <= 64.0;
    }

    @Override
    public ItemStack quickMoveStack(Player player, int slot) {
        return MOVE_HELPER.quickMoveStack(this, player, slot);
    }
}
