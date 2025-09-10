package dev.hardaway.mannequins.common.entity;

import dev.hardaway.mannequins.client.screen.DummyEditorScreen;
import dev.hardaway.mannequins.common.block.entity.DummyBlockEntity;
import dev.hardaway.mannequins.core.registry.MannequinsEntities;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ClientDummy extends LivingEntity {

    private final DummyBlockEntity dummy;

    public ClientDummy(EntityType<? extends ClientDummy> entityType, Level level, DummyBlockEntity dummy) {
        super(entityType, level);
        this.dummy = dummy;
    }

    public ClientDummy(EntityType<? extends ClientDummy> entityType, Level level) {
        this(entityType, level, null);
    }

    public ClientDummy(Level level, DummyBlockEntity dummy) {
        this(MannequinsEntities.DUMMY.get(), level, dummy);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return createLivingAttributes().add(Attributes.STEP_HEIGHT, 0.0);
    }

    @Override
    public Iterable<ItemStack> getArmorSlots() {
        return dummy.getInventory().getArmorSlots();
    }

    @Override
    public ItemStack getItemBySlot(EquipmentSlot slot) {
        return dummy.getInventory().getItemBySlot(slot);
    }

    @Override
    public Iterable<ItemStack> getHandSlots() {
        return dummy.getInventory().getHandSlots();
    }

    @Override
    public void setItemSlot(EquipmentSlot slot, ItemStack stack) {
        this.dummy.getInventory().setItemSlot(slot, stack);
    }

    @Override
    public HumanoidArm getMainArm() {
        return HumanoidArm.RIGHT;
    }

    public DummyBlockEntity getDummy() {
        return dummy;
    }
}
