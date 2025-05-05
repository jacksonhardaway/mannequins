package dev.hardaway.mannequins.common.entity;

import dev.hardaway.mannequins.common.component.MannequinPose;
import dev.hardaway.mannequins.common.menu.MannequinInventory;
import dev.hardaway.mannequins.core.registry.MannequinsEntities;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class Dummy extends LivingEntity {

    private final MannequinInventory inventory;
    private MannequinPose mannequinPose;

    public Dummy(EntityType<? extends Dummy> entityType, Level level, MannequinPose mannequinPose, MannequinInventory inventory) {
        super(entityType, level);
        this.inventory = inventory;
        this.mannequinPose = mannequinPose;
    }

    public Dummy(EntityType<? extends Dummy> entityType, Level level) {
        this(entityType, level, MannequinPose.NONE, new MannequinInventory());
    }

    public Dummy(Level level, MannequinPose mannequinPose, MannequinInventory inventory) {
        this(MannequinsEntities.DUMMY.get(), level, MannequinPose.NONE, inventory);
        this.mannequinPose = mannequinPose;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return createLivingAttributes().add(Attributes.STEP_HEIGHT, 0.0);
    }

    @Override
    public Iterable<ItemStack> getArmorSlots() {
        return inventory.getArmorSlots();
    }

    @Override
    public ItemStack getItemBySlot(EquipmentSlot slot) {
        return inventory.getItemBySlot(slot);
    }

    @Override
    public Iterable<ItemStack> getHandSlots() {
        return inventory.getHandSlots();
    }

    @Override
    public void setItemSlot(EquipmentSlot slot, ItemStack stack) {
        this.inventory.setItemSlot(slot, stack);
    }

    @Override
    public HumanoidArm getMainArm() {
        return HumanoidArm.RIGHT;
    }

    public MannequinPose getMannequinPose() {
        return mannequinPose;
    }

    public void setMannequinPose(MannequinPose mannequinPose) {
        this.mannequinPose = mannequinPose;
    }
}
