package me.jaackson.mannequins.bridge;

import me.jaackson.mannequins.common.network.MannequinsPacket;
import me.shedaniel.architectury.annotations.ExpectPlatform;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.Packet;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * @author Jackson
 */
public final class NetworkBridge {

    @ExpectPlatform
    public static <T extends MannequinsPacket> void registerPlayToClient(ResourceLocation channel, Class<T> messageType, Function<FriendlyByteBuf, T> read, Supplier<Consumer<T>> handle) {
        Platform.safeAssertionError();
    }

    @ExpectPlatform
    public static <T extends MannequinsPacket> void registerPlayToServer(ResourceLocation channel, Class<T> messageType, Function<FriendlyByteBuf, T> read, BiConsumer<T, Player> handle) {
        Platform.safeAssertionError();
    }

    @ExpectPlatform
    public static void sendToPlayer(ServerPlayer player, MannequinsPacket packet) {
        Platform.safeAssertionError();
    }

    @ExpectPlatform
    public static void sendToTracking(Entity tracking, MannequinsPacket packet) {
        Platform.safeAssertionError();
    }

    @ExpectPlatform
    public static void sendToNear(ServerLevel level, double x, double y, double z, double distance, MannequinsPacket packet) {
        Platform.safeAssertionError();
    }

    @ExpectPlatform
    public static void sendToServer(MannequinsPacket packet) {
        Platform.safeAssertionError();
    }

    @ExpectPlatform
    public static Packet<?> toVanillaPacket(MannequinsPacket packet, boolean clientbound) {
        throw new AssertionError();
    }
}
