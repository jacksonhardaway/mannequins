package me.jaackson.mannequins.bridge;

import me.shedaniel.architectury.annotations.ExpectPlatform;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;

public final class EventBridge {

    @ExpectPlatform
    public static void fireContainerOpenEvent(ServerPlayer player, AbstractContainerMenu menu) {
        Platform.safeAssertionError();
    }

    @ExpectPlatform
    public static void registerRightClickItemEvent(RightClickItemCallback callback) {
        Platform.safeAssertionError();
    }

    @FunctionalInterface
    public interface RightClickItemCallback {
        void clickItem(Player player, Level level, InteractionHand hand);
    }
}
