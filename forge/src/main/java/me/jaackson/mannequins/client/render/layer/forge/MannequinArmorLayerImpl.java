package me.jaackson.mannequins.client.render.layer.forge;

import me.jaackson.mannequins.client.render.model.MannequinModel;
import me.jaackson.mannequins.common.entity.Mannequin;
import me.jaackson.mannequins.forge.mixin.client.HumanoidArmorLayerAccessor;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

/**
 * @author Jackson
 */
public class MannequinArmorLayerImpl {

    public static ResourceLocation getArmorTexture(HumanoidArmorLayer<Mannequin, MannequinModel, MannequinModel> layer, Entity entity, ItemStack stack, EquipmentSlot slot, boolean innerModel, @Nullable String type) {
        return layer.getArmorResource(entity, stack, slot, type);
    }

    @SuppressWarnings("unchecked")
    public static MannequinModel getArmorModel(HumanoidArmorLayer<Mannequin, MannequinModel, MannequinModel> layer, Mannequin entity, ItemStack itemStack, EquipmentSlot slot, MannequinModel model) {
        return ((HumanoidArmorLayerAccessor<Mannequin, MannequinModel, MannequinModel>) layer).callGetArmorModelHook(entity, itemStack, slot, model);
    }
}
