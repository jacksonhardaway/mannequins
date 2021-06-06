package me.jaackson.mannequins.client.render.layer.fabric;

import me.jaackson.mannequins.client.render.model.MannequinModel;
import me.jaackson.mannequins.common.entity.Mannequin;
import net.minecraft.client.renderer.entity.layers.ElytraLayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class MannequinElytraLayerImpl {
    private static final ResourceLocation WINGS_LOCATION = new ResourceLocation("textures/entity/elytra.png");

    public static boolean canRender(ElytraLayer<Mannequin, MannequinModel> layer, ItemStack stack, Mannequin entity) {
        return stack.getItem() == Items.ELYTRA;
    }

    public static ResourceLocation getTexture(ElytraLayer<Mannequin, MannequinModel> layer, ItemStack stack, Mannequin entity) {
        return WINGS_LOCATION;
    }
}
