package gg.moonflower.mannequins.core.fabric;

import gg.moonflower.mannequins.common.entity.AbstractMannequin;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.event.client.player.ClientPickBlockGatherCallback;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

/**
 * @author Jackson
 */
public class MannequinsFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // TODO: add api
        ClientPickBlockGatherCallback.EVENT.register((player, result) -> {
            HitResult.Type type = result.getType();
            if (type != HitResult.Type.ENTITY || !player.getAbilities().instabuild)
                return ItemStack.EMPTY;

            Entity entity = ((EntityHitResult) result).getEntity();
            if (entity instanceof AbstractMannequin) {
                return ((AbstractMannequin) entity).getPickedResult(result);
            }

            return ItemStack.EMPTY;
        });
    }
}
