package gg.moonflower.mannequins.common.network.play;

import gg.moonflower.mannequins.common.network.play.handler.MannequinsServerPlayPacketHandler;
import gg.moonflower.pollen.api.network.packet.PollinatedPacket;
import gg.moonflower.pollen.api.network.packet.PollinatedPacketContext;
import net.minecraft.core.Rotations;
import net.minecraft.network.FriendlyByteBuf;

/**
 * @author Ocelot
 */
public class ServerboundSetMannequinPose implements PollinatedPacket<MannequinsServerPlayPacketHandler> {
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
    public void writePacketData(FriendlyByteBuf buf) {
        buf.writeByte(this.containerId);
        writeRotations(buf, this.headRotations);
        writeRotations(buf, this.bodyRotations);
        writeRotations(buf, this.leftArmRotations);
        writeRotations(buf, this.rightArmRotations);
    }

    @Override
    public void processPacket(MannequinsServerPlayPacketHandler handler, PollinatedPacketContext ctx) {
        handler.handleSetMannequinPose(this, ctx);
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