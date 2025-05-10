package dev.hardaway.mannequins.client.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.hardaway.mannequins.core.mixin.client.HumanoidModelAccessor;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.world.entity.HumanoidArm;

import java.util.Collections;

public class MannequinModel extends DummyModel implements TranslatedHumanoid {
    private final ModelPart stand;
    private final ModelPart root;

    public MannequinModel(ModelPart root) {
        super(root);
        this.stand = root.getChild("stand");
        this.root = this.stand.getChild("root");

        HumanoidModelAccessor access = (HumanoidModelAccessor) this;
        access.setBody(this.root.getChild("body"));
        access.setLeftArm(this.body.getChild("left_arm"));
        access.setRightArm(this.body.getChild("right_arm"));
        access.setHead(this.body.getChild("head"));
    }

    @Override
    public ModelPart getRoot() {
        return root;
    }

    @Override
    protected Iterable<ModelPart> headParts() {
        return Collections.emptySet();
    }

    @Override
    protected Iterable<ModelPart> bodyParts() {
        return ImmutableList.of(this.stand);
    }

    @Override
    public void translateToHand(HumanoidArm arm, PoseStack poseStack) {
        this.stand.translateAndRotate(poseStack);
        this.getRoot().translateAndRotate(poseStack);
        this.body.translateAndRotate(poseStack);
        super.translateToHand(arm, poseStack);
    }

    public void translateToHead(PoseStack poseStack) {
        this.stand.translateAndRotate(poseStack);
        this.getRoot().translateAndRotate(poseStack);
        this.body.translateAndRotate(poseStack);
    }

    public void translateToBody(PoseStack poseStack) {
        this.stand.translateAndRotate(poseStack);
        this.getRoot().translateAndRotate(poseStack);
        this.body.translateAndRotate(poseStack);
    }

    public void translateToElytra(PoseStack poseStack) {
        this.stand.translateAndRotate(poseStack);
        this.getRoot().translateAndRotate(poseStack);
        this.body.translateAndRotate(poseStack);
        poseStack.translate(0, this.head.y / 16F, 0);
    }

    // TODO: make model body only, no stand
    // TODO: make stand json
    // TODO: make editor json?
    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = HumanoidModel.createMesh(CubeDeformation.NONE, 0.0F);
        PartDefinition modelRoot = mesh.getRoot();

        PartDefinition stand = modelRoot.addOrReplaceChild("stand", CubeListBuilder.create().texOffs(24, 16).addBox(-1.0F, -12.0F, -1.0F, 2.0F, 12.0F, 2.0F, false), PartPose.offset(0.0F, 24.0F, 0.0F));
        PartDefinition root = stand.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0.0F, -12.0F, 0.0F));

        PartDefinition body = root.addOrReplaceChild("body", CubeListBuilder.create()
                        .texOffs(0, 16)
                        .addBox(-4.0F, 0.0F, -2.0F, 8.0F, 10.0F, 4.0F, false),
                PartPose.offset(0.0F, -10.0F, 0.0F));
        body.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(48, 0).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, false), PartPose.offset(5.0F, 2.0F, 0.0F));
        body.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(32, 0).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, false), PartPose.offset(-5.0F, 2.0F, 0.0F));
        body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, false), PartPose.offset(0.0F, 0.0F, 0.0F));

        return LayerDefinition.create(mesh, 64, 64);
    }
}