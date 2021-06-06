package me.jaackson.mannequins.client.render.model;

import me.jaackson.mannequins.common.entity.Mannequin;
import net.minecraft.client.model.HumanoidModel;

/**
 * @author Echolite, Jackson
 */
public class MannequinModel extends HumanoidModel<Mannequin> {
    public MannequinModel(float inflate) {
        this(inflate, 64, 32);
    }

    protected MannequinModel(float inflate, int texWidth, int texHeight) {
        super(inflate, 0.0F, texWidth, texHeight);
    }

    @Override
    public void setupAnim(Mannequin entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.head.xRot = ((float) Math.PI / 180F) * entity.getHeadPose().getX();
        this.head.yRot = ((float) Math.PI / 180F) * entity.getHeadPose().getY();
        this.head.zRot = ((float) Math.PI / 180F) * entity.getHeadPose().getZ();
        this.body.xRot = ((float) Math.PI / 180F) * entity.getBodyPose().getX();
        this.body.yRot = ((float) Math.PI / 180F) * entity.getBodyPose().getY();
        this.body.zRot = ((float) Math.PI / 180F) * entity.getBodyPose().getZ();
        this.leftArm.xRot = ((float) Math.PI / 180F) * entity.getLeftArmPose().getX();
        this.leftArm.yRot = ((float) Math.PI / 180F) * entity.getLeftArmPose().getY();
        this.leftArm.zRot = ((float) Math.PI / 180F) * entity.getLeftArmPose().getZ();
        this.rightArm.xRot = ((float) Math.PI / 180F) * entity.getRightArmPose().getX();
        this.rightArm.yRot = ((float) Math.PI / 180F) * entity.getRightArmPose().getY();
        this.rightArm.zRot = ((float) Math.PI / 180F) * entity.getRightArmPose().getZ();
        this.hat.copyFrom(this.head);

        this.leftLeg.visible = false;
        this.rightLeg.visible = false;
    }
}