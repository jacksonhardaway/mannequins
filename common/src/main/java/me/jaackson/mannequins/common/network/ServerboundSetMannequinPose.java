package me.jaackson.mannequins.common.network;

import me.jaackson.mannequins.Mannequins;
import me.jaackson.mannequins.bridge.PlayerBridge;
import me.jaackson.mannequins.common.menu.MannequinInventoryMenu;
import net.minecraft.core.Rotations;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

/**
 * @author Ocelot
 */
public class ServerboundSetMannequinPose implements MannequinsPacket {
    public static final ResourceLocation CHANNEL = new ResourceLocation(Mannequins.MOD_ID, "set_mannequin_pose");
    private static final Component DISCONNECT_MESSAGE = new TranslatableComponent("disconnect." + Mannequins.MOD_ID + ".invalid_mannequin_pose");

    private final int containerId;
    private final Rotations headRotations;
    private final Rotations bodyRotations;
    private final Rotations leftArmRotations;
    private final Rotations rightArmRotations;

    public ServerboundSetMannequinPose(int id, Rotations headRotations, Rotations bodyRotations, Rotations leftArmRotations, Rotations rightArmRotations) {
        this.containerId = id;
        this.headRotations = headRotations;
        this.bodyRotations = bodyRotations;
        this.leftArmRotations = leftArmRotations;
        this.rightArmRotations = rightArmRotations;
    }

    private static void writeRotations(FriendlyByteBuf buf, Rotations rotations) {
        buf.writeFloat(rotations.getX());
        buf.writeFloat(rotations.getY());
        buf.writeFloat(rotations.getZ());
    }

    private static Rotations readRotations(FriendlyByteBuf buf) {
        return new Rotations(buf.readFloat(), buf.readFloat(), buf.readFloat());
    }

    public static ServerboundSetMannequinPose read(FriendlyByteBuf buf) {
        return new ServerboundSetMannequinPose(buf.readUnsignedByte(), readRotations(buf), readRotations(buf), readRotations(buf), readRotations(buf));
    }

    public static void handle(ServerboundSetMannequinPose packet, Player player) {

        if (!(player instanceof ServerPlayer))
            return;

        ServerPlayer serverPlayer = (ServerPlayer) player;
        if (PlayerBridge.getContainerId(serverPlayer) != packet.containerId)
            return;

        if (!(serverPlayer.containerMenu instanceof MannequinInventoryMenu) || !serverPlayer.containerMenu.stillValid(serverPlayer)) {
            serverPlayer.connection.disconnect(DISCONNECT_MESSAGE);
            return;
        }

        ((MannequinInventoryMenu) serverPlayer.containerMenu).setMannequinPose(packet.headRotations, packet.bodyRotations, packet.leftArmRotations, packet.rightArmRotations);
    }

    public FriendlyByteBuf write(FriendlyByteBuf buf) {
        buf.writeByte(this.containerId);
        writeRotations(buf, this.headRotations);
        writeRotations(buf, this.bodyRotations);
        writeRotations(buf, this.leftArmRotations);
        writeRotations(buf, this.rightArmRotations);
        return buf;
    }
}