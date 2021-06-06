package me.jaackson.mannequins.bridge;

import me.jaackson.mannequins.common.network.MannequinsPacket;
import me.shedaniel.architectury.annotations.ExpectPlatform;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author Jackson
 */
public final class NetworkBridge {

    @ExpectPlatform
    public static <T> void registerClientbound(ResourceLocation channel, Class<T> messageType, BiConsumer<T, FriendlyByteBuf> write, Function<FriendlyByteBuf, T> read, Consumer<T> handle) {
        Platform.safeAssertionError();
    }

    @ExpectPlatform
    public static <T> void registerServerbound(ResourceLocation channel, Class<T> messageType, BiConsumer<T, FriendlyByteBuf> write, Function<FriendlyByteBuf, T> read, BiConsumer<T, Player> handle) {
        Platform.safeAssertionError();
    }

    @ExpectPlatform
    public static void sendClientbound(ResourceLocation channel, ServerPlayer player, MannequinsPacket packet) {
        Platform.safeAssertionError();
    }

    @ExpectPlatform
    public static void sendClientboundTracking(ResourceLocation channel, Entity tracking, MannequinsPacket packet) {
        Platform.safeAssertionError();
    }

    @ExpectPlatform
    public static void sendServerbound(ResourceLocation channel, MannequinsPacket packet) {
        Platform.safeAssertionError();
    }
}
