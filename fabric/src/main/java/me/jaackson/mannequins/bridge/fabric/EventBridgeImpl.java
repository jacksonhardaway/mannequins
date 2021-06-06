package me.jaackson.mannequins.bridge.fabric;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.AbstractContainerMenu;

public class EventBridgeImpl {
    public static void fireContainerOpenEvent(ServerPlayer player, AbstractContainerMenu menu) {
        // Do nothing, this is a Forge event.
    }
}
