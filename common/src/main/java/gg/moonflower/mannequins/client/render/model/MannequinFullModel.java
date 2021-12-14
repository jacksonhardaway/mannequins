package gg.moonflower.mannequins.client.render.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import gg.moonflower.mannequins.common.entity.Mannequin;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.HumanoidArm;

import java.util.Collections;

/**
 * @author Echolite, Jackson, Ocelot
 */
public class MannequinFullModel extends MannequinModel {
    private final ModelPart stand;
    private final ModelPart baseplate;

    public MannequinFullModel(ModelPart root) {
        super(root);
        this.baseplate = root.getChild("baseplate");
        this.stand = this.baseplate.getChild("stand");
        this.body = this.stand.getChild("body");
        this.rightArm = this.body.getChild("rightArm");
        this.leftArm = this.body.getChild("leftArm");
        this.head = this.body.getChild("head");
    }

    public static LayerDefinition createLayerDefinition() {
        MeshDefinition meshDefinition = HumanoidModel.createMesh(CubeDeformation.NONE, 0.0F);
        PartDefinition root = meshDefinition.getRoot();
        PartDefinition baseplate = root.addOrReplaceChild("baseplate", CubeListBuilder.create().texOffs(24, 52).addBox(-5.0F, -2.0F, -5.0F, 10.0F, 2.0F, 10.0F, false), PartPose.offsetAndRotation(0.0F, 24.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        PartDefinition stand = baseplate.addOrReplaceChild("stand", CubeListBuilder.create().texOffs(0, 50).addBox(-1.0F, -12.0F, -1.0F, 2.0F, 12.0F, 2.0F, false), PartPose.offsetAndRotation(0.0F, -2.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        PartDefinition body = stand.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 16).addBox(-4.0F, -10.0F, -2.0F, 8.0F, 10.0F, 4.0F, false), PartPose.offsetAndRotation(0.0F, -12.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        PartDefinition rightArm = body.addOrReplaceChild("rightArm", CubeListBuilder.create().texOffs(32, 0).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, false), PartPose.offsetAndRotation(-5.0F, -8.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        PartDefinition leftArm = body.addOrReplaceChild("leftArm", CubeListBuilder.create().texOffs(48, 0).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, false), PartPose.offsetAndRotation(5.0F, -8.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        PartDefinition head = body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, false), PartPose.offsetAndRotation(0.0F, -10.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        return LayerDefinition.create(meshDefinition, 64, 64);
    }

    @Override
    public void translateToHand(HumanoidArm arm, PoseStack poseStack) {
        this.baseplate.translateAndRotate(poseStack);
        this.stand.translateAndRotate(poseStack);
        this.body.translateAndRotate(poseStack);
        super.translateToHand(arm, poseStack);
    }

    public void translateToHead(PoseStack poseStack) {
        this.baseplate.translateAndRotate(poseStack);
        this.stand.translateAndRotate(poseStack);
        this.body.translateAndRotate(poseStack);
    }

    public void translateToBody(PoseStack poseStack) {
        this.baseplate.translateAndRotate(poseStack);
        this.stand.translateAndRotate(poseStack);
        this.body.translateAndRotate(poseStack);
    }

    public void translateToElytra(PoseStack poseStack) {
        this.baseplate.translateAndRotate(poseStack);
        this.stand.translateAndRotate(poseStack);
        this.body.translateAndRotate(poseStack);
        poseStack.translate(0, this.head.y / 16F, 0);
    }

    @Override
    public void setupAnim(Mannequin entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);

        if (entity.hasAnimation()) {
            Minecraft minecraft = Minecraft.getInstance();
            this.stand.xRot = -(entity.getAnimationRotationX(minecraft.getFrameTime()) * 45F) * ((float) Math.PI / 180F);
            this.stand.zRot = (entity.getAnimationRotationZ(minecraft.getFrameTime()) * 45F) * ((float) Math.PI / 180F);
        } else {
            this.stand.xRot = 0;
            this.stand.zRot = 0;
        }
    }

    @Override
    protected Iterable<ModelPart> headParts() {
        return Collections.emptySet();
    }

    @Override
    protected Iterable<ModelPart> bodyParts() {
        return ImmutableList.of(this.baseplate);
    }
}