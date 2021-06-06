package me.jaackson.mannequins.bridge;

import me.shedaniel.architectury.annotations.ExpectPlatform;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.inventory.AbstractContainerMenu;

public final class EventBridge {

    @ExpectPlatform
    public static void fireContainerOpenEvent(ServerPlayer player, AbstractContainerMenu menu) {
        Platform.safeAssertionError();
    }
}
