package me.jaackson.mannequins.forge.mixin.client;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(HumanoidArmorLayer.class)
public interface HumanoidArmorLayerAccessor<T extends LivingEntity, M extends HumanoidModel<T>, A extends HumanoidModel<T>> {

    @Invoker
    A callGetArmorModelHook(T entity, ItemStack itemStack, EquipmentSlot slot, A model);
}
