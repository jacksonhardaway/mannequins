package me.jaackson.mannequins.common.network;

import me.jaackson.mannequins.Mannequins;
import net.minecraft.core.Rotations;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

/**
 * @author Ocelot
 */
public class ServerboundSetMannequinPose implements MannequinsPacket {
    public static final ResourceLocation CHANNEL = new ResourceLocation(Mannequins.MOD_ID, "set_mannequin_pose");

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

    public ServerboundSetMannequinPose(FriendlyByteBuf buf) {
        this.containerId = buf.readUnsignedByte();
        this.headRotations = readRotations(buf);
        this.bodyRotations = readRotations(buf);
        this.leftArmRotations = readRotations(buf);
        this.rightArmRotations = readRotations(buf);
    }

    private static Rotations readRotations(FriendlyByteBuf buf) {
        return new Rotations(buf.readFloat(), buf.readFloat(), buf.readFloat());
    }

    private static void writeRotations(FriendlyByteBuf buf, Rotations rotations) {
        buf.writeFloat(rotations.getX());
        buf.writeFloat(rotations.getY());
        buf.writeFloat(rotations.getZ());
    }

    @Override
    public void write(FriendlyByteBuf buf) {
        buf.writeByte(this.containerId);
        writeRotations(buf, this.headRotations);
        writeRotations(buf, this.bodyRotations);
        writeRotations(buf, this.leftArmRotations);
        writeRotations(buf, this.rightArmRotations);
    }

    @Override
    public ResourceLocation getChannel() {
        return CHANNEL;
    }

    public int getContainerId() {
        return containerId;
    }

    public Rotations getHeadRotations() {
        return headRotations;
    }

    public Rotations getBodyRotations() {
        return bodyRotations;
    }

    public Rotations getLeftArmRotations() {
        return leftArmRotations;
    }

    public Rotations getRightArmRotations() {
        return rightArmRotations;
    }
}