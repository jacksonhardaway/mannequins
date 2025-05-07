package dev.hardaway.mannequins.common.entity;

import dev.hardaway.mannequins.common.block.entity.MannequinBlockEntity;
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

    private final MannequinBlockEntity mannequin;

    public Dummy(EntityType<? extends Dummy> entityType, Level level, MannequinBlockEntity mannequin) {
        super(entityType, level);
        this.mannequin = mannequin;
    }

    public Dummy(EntityType<? extends Dummy> entityType, Level level) {
        this(entityType, level, null);
    }

    public Dummy(Level level, MannequinBlockEntity mannequin) {
        this(MannequinsEntities.DUMMY.get(), level, mannequin);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return createLivingAttributes().add(Attributes.STEP_HEIGHT, 0.0);
    }

    @Override
    public Iterable<ItemStack> getArmorSlots() {
        return mannequin.getInventory().getArmorSlots();
    }

    @Override
    public ItemStack getItemBySlot(EquipmentSlot slot) {
        return mannequin.getInventory().getItemBySlot(slot);
    }

    @Override
    public Iterable<ItemStack> getHandSlots() {
        return mannequin.getInventory().getHandSlots();
    }

    @Override
    public void setItemSlot(EquipmentSlot slot, ItemStack stack) {
        this.mannequin.getInventory().setItemSlot(slot, stack);
    }

    @Override
    public HumanoidArm getMainArm() {
        return HumanoidArm.RIGHT;
    }

    public MannequinBlockEntity getMannequin() {
        return mannequin;
    }
}
