package me.jaackson.mannequins.bridge.fabric;

import me.jaackson.mannequins.bridge.EventBridge;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.inventory.AbstractContainerMenu;

public class EventBridgeImpl {
    public static void fireContainerOpenEvent(ServerPlayer player, AbstractContainerMenu menu) {
        // Do nothing, this is a Forge event.
    }

    public static void registerRightClickItemEvent(EventBridge.RightClickItemCallback callback) {
        UseItemCallback.EVENT.register((player, world, hand) -> {
            callback.clickItem(player, world, hand);
            return InteractionResultHolder.pass(player.getItemInHand(hand));
        });
    }
}
