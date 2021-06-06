package me.jaackson.mannequins.bridge.forge;

import me.jaackson.mannequins.bridge.EventBridge;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerContainerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

public class EventBridgeImpl {
    public static void fireContainerOpenEvent(ServerPlayer player, AbstractContainerMenu menu) {
        MinecraftForge.EVENT_BUS.post(new PlayerContainerEvent.Open(player, menu));
    }

    public static void registerRightClickItemEvent(EventBridge.RightClickItemCallback callback) {
        MinecraftForge.EVENT_BUS.<PlayerInteractEvent.RightClickItem>addListener(e -> callback.clickItem(e.getPlayer(), e.getWorld(), e.getHand()));
    }
}
