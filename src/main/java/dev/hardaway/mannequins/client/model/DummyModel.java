package dev.hardaway.mannequins.client.model;

import dev.hardaway.mannequins.common.block.entity.MannequinBlockEntity;
import dev.hardaway.mannequins.common.component.MannequinPose;
import dev.hardaway.mannequins.common.entity.Dummy;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;

public class DummyModel extends HumanoidModel<Dummy> {

    public DummyModel(ModelPart root) {
        super(root);
    }

    public static LayerDefinition createMesh(CubeDeformation deformation) {
        MeshDefinition meshDefinition = HumanoidModel.createMesh(deformation, 0.0F);
        return LayerDefinition.create(meshDefinition, 64, 32);
    }

    @Override
    public void setupAnim(Dummy entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.leftLeg.visible = false;
        this.rightLeg.visible = false;

        MannequinPose pose = entity.getMannequinPose();
        this.head.xRot = ((float) Math.PI / 180F) * pose.head().getX();
        this.head.yRot = ((float) Math.PI / 180F) * pose.head().getY();
        this.head.zRot = ((float) Math.PI / 180F) * pose.head().getZ();
        this.body.xRot = ((float) Math.PI / 180F) * pose.body().getX();
        this.body.yRot = ((float) Math.PI / 180F) * pose.body().getY();
        this.body.zRot = ((float) Math.PI / 180F) * pose.body().getZ();
        this.leftArm.xRot = ((float) Math.PI / 180F) * pose.leftArm().getX();
        this.leftArm.yRot = ((float) Math.PI / 180F) * pose.leftArm().getY();
        this.leftArm.zRot = ((float) Math.PI / 180F) * pose.leftArm().getZ();
        this.rightArm.xRot = ((float) Math.PI / 180F) * pose.rightArm().getX();
        this.rightArm.yRot = ((float) Math.PI / 180F) * pose.rightArm().getY();
        this.rightArm.zRot = ((float) Math.PI / 180F) * pose.rightArm().getZ();
        this.hat.copyFrom(this.head);
    }
}