package dev.hardaway.mannequins.client.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.hardaway.mannequins.api.DummyPose;
import dev.hardaway.mannequins.common.entity.ClientDummy;
import dev.hardaway.mannequins.core.mixin.client.HumanoidModelAccessor;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.world.entity.HumanoidArm;

import java.util.Collections;

public class DummyModel extends HumanoidModel<ClientDummy> {

    public DummyModel(ModelPart root) {
        super(root);
    }

    public ModelPart getRoot() {
        return body;
    }

    public static LayerDefinition createBodyLayer(CubeDeformation deformation) {
        MeshDefinition meshDefinition = HumanoidModel.createMesh(deformation, 0.0F);
        return LayerDefinition.create(meshDefinition, 64, 32);
    }

    @Override
    public void setupAnim(ClientDummy entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.leftLeg.visible = false;
        this.rightLeg.visible = false;

        DummyPose pose = entity.getDummy().getPose();
        this.head.xRot = ((float) Math.PI / 180F) * pose.head().getX();
        this.head.yRot = ((float) Math.PI / 180F) * pose.head().getY();
        this.head.zRot = ((float) Math.PI / 180F) * pose.head().getZ();
        this.getRoot().xRot = ((float) Math.PI / 180F) * pose.body().getX();
        this.getRoot().yRot = ((float) Math.PI / 180F) * pose.body().getY();
        this.getRoot().zRot = ((float) Math.PI / 180F) * pose.body().getZ();
        this.leftArm.xRot = ((float) Math.PI / 180F) * pose.leftArm().getX();
        this.leftArm.yRot = ((float) Math.PI / 180F) * pose.leftArm().getY();
        this.leftArm.zRot = ((float) Math.PI / 180F) * pose.leftArm().getZ();
        this.rightArm.xRot = ((float) Math.PI / 180F) * pose.rightArm().getX();
        this.rightArm.yRot = ((float) Math.PI / 180F) * pose.rightArm().getY();
        this.rightArm.zRot = ((float) Math.PI / 180F) * pose.rightArm().getZ();
        this.hat.copyFrom(this.head);
    }
}