package me.jaackson.mannequins.bridge.fabric;

import me.jaackson.mannequins.common.network.MannequinsPacket;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PlayerLookup;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

public class NetworkBridgeImpl {
    public static <T> void registerClientbound(ResourceLocation channel, Class<T> messageType, BiConsumer<T, FriendlyByteBuf> write, Function<FriendlyByteBuf, T> read, Consumer<T> handle) {
        ClientPlayNetworking.registerGlobalReceiver(channel, (client, handler, buf, responseSender) -> handle.accept(read.apply(buf)));
    }

    public static <T> void registerServerbound(ResourceLocation channel, Class<T> messageType, BiConsumer<T, FriendlyByteBuf> write, Function<FriendlyByteBuf, T> read, BiConsumer<T, Player> handle) {
        ServerPlayNetworking.registerGlobalReceiver(channel, (server, player, handler, buf, responseSender) -> handle.accept(read.apply(buf), player));
    }

    public static void sendClientbound(ResourceLocation channel, ServerPlayer player, MannequinsPacket packet) {
        ServerPlayNetworking.send(player, channel, packet.write(PacketByteBufs.create()));
    }

    public static void sendClientboundTracking(ResourceLocation channel, Entity tracking, MannequinsPacket packet) {
        for (ServerPlayer player : PlayerLookup.tracking(tracking)) {
            ServerPlayNetworking.send(player, channel, packet.write(PacketByteBufs.create()));
        }
    }

    public static void sendServerbound(ResourceLocation channel, MannequinsPacket packet) {
        ClientPlayNetworking.send(channel, packet.write(PacketByteBufs.create()));
    }
}
