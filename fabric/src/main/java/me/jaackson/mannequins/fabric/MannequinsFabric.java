package me.jaackson.mannequins.fabric;

import me.jaackson.mannequins.Mannequins;
import me.jaackson.mannequins.common.entity.Mannequin;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.client.player.ClientPickBlockGatherCallback;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;

/**
 * @author Jackson
 */
public class MannequinsFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        Mannequins.init();
        Mannequins.commonSetup();

        ClientPickBlockGatherCallback.EVENT.register((player, result) -> {
            HitResult.Type type = result.getType();
            if (result == null || type != HitResult.Type.ENTITY || !player.abilities.instabuild)
                return ItemStack.EMPTY;

            Entity entity = ((EntityHitResult) result).getEntity();
            if (entity instanceof Mannequin) {
                return new ItemStack(Mannequins.mannequinItem.get());
            }

            return ItemStack.EMPTY;
        });
    }

}
