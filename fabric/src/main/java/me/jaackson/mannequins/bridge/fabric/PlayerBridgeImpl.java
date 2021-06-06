package me.jaackson.mannequins.bridge.fabric;

import me.jaackson.mannequins.fabric.mixin.ServerPlayerAccessor;
import net.minecraft.server.level.ServerPlayer;

/**
 * @author Jackson
 */
public class PlayerBridgeImpl {

    public static void incrementContainerId(ServerPlayer player) {
        ((ServerPlayerAccessor) player).callNextContainerCounter();
    }

    public static int getContainerId(ServerPlayer player) {
        return ((ServerPlayerAccessor) player).getContainerCounter();
    }
}
