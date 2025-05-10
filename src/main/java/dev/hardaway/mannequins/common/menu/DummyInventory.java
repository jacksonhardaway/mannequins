package dev.hardaway.mannequins.common.menu;

import com.google.common.collect.ImmutableList;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.ItemStackHandler;

public class DummyInventory extends ItemStackHandler {

    public DummyInventory() {
        super(4);
    }

    public Iterable<ItemStack> getArmorSlots() {
        return ImmutableList.of(this.getStackInSlot(0), this.getStackInSlot(1));
    }

    public Iterable<ItemStack> getHandSlots() {
        return ImmutableList.of(this.getStackInSlot(2), this.getStackInSlot(3));
    }

    public ItemStack getItemBySlot(EquipmentSlot slot) {
        if (slot == null)
            return ItemStack.EMPTY;

        return switch (slot) {
            case HEAD -> this.getStackInSlot(0);
            case CHEST -> this.getStackInSlot(1);
            case MAINHAND -> this.getStackInSlot(2);
            case OFFHAND -> this.getStackInSlot(3);
            default -> ItemStack.EMPTY;
        };
    }

    public void setItemSlot(EquipmentSlot slot, ItemStack stack) {
        if (slot == null)
            return;

        switch (slot) {
            case HEAD -> this.setStackInSlot(0, stack);
            case CHEST -> this.setStackInSlot(1, stack);
            case MAINHAND -> this.setStackInSlot(2, stack);
            case OFFHAND -> this.setStackInSlot(3, stack);
        }
    }

}
