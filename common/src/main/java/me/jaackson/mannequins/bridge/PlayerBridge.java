package me.jaackson.mannequins.bridge;

import me.shedaniel.architectury.annotations.ExpectPlatform;
import net.minecraft.server.level.ServerPlayer;

/**
 * @author Jackson
 */
public final class PlayerBridge {

    @ExpectPlatform
    public static void incrementContainerId(ServerPlayer player) {
        Platform.safeAssertionError();
    }

    @ExpectPlatform
    public static int getContainerId(ServerPlayer player) {
        return Platform.safeAssertionError();
    }
}
