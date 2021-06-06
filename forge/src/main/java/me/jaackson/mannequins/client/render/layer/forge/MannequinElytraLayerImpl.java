package me.jaackson.mannequins.client.render.layer.forge;

import me.jaackson.mannequins.client.render.model.MannequinModel;
import me.jaackson.mannequins.common.entity.Mannequin;
import net.minecraft.client.renderer.entity.layers.ElytraLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

/**
 * @author Jackson
 */
public class MannequinElytraLayerImpl {

    public static boolean canRender(ElytraLayer<Mannequin, MannequinModel> layer, ItemStack stack, Mannequin entity) {
        return layer.shouldRender(stack, entity);
    }

    public static ResourceLocation getTexture(ElytraLayer<Mannequin, MannequinModel> layer, ItemStack stack, Mannequin entity) {
        return layer.getElytraTexture(stack, entity);
    }

}
