package me.jaackson.mannequins.bridge.forge;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;

public class EventBridgeImpl {
    public static void fireContainerOpenEvent(ServerPlayer player, AbstractContainerMenu menu) {
        MinecraftForge.EVENT_BUS.post(new PlayerContainerEvent.Open(player, menu));
    }
}
