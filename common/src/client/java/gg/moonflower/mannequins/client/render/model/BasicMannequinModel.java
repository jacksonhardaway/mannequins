package gg.moonflower.mannequins.client.render.model;

import gg.moonflower.mannequins.common.entity.AbstractMannequin;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;

/**
 * @author Echolite, Jackson
 */
public class BasicMannequinModel<T extends AbstractMannequin> extends HumanoidModel<T> {

    public BasicMannequinModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition createLayerDefinition(CubeDeformation deformation) {
        MeshDefinition meshDefinition = createMesh(deformation, 0.0F);
        return LayerDefinition.create(meshDefinition, 64, 32);
    }

    @Override
    public void setupAnim(AbstractMannequin entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
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