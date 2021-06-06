package me.jaackson.mannequins.bridge.forge;

import net.minecraft.server.level.ServerPlayer;

/**
 * @author Jackson
 */
public class PlayerBridgeImpl {

    public static void incrementContainerId(ServerPlayer player) {
        player.nextContainerCounter();
    }

    public static int getContainerId(ServerPlayer player) {
        return player.containerCounter;

    }
}
