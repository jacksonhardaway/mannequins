package gg.moonflower.mannequins.client.render.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import gg.moonflower.mannequins.common.entity.Statue;
import gg.moonflower.mannequins.core.mixin.client.HumanoidModelAccessor;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.world.entity.HumanoidArm;

import java.util.Collections;

/**
 * @author Echolite
 */
public class StatueModel extends BasicMannequinModel<Statue> implements TranslatedMannequin {
    private final ModelPart stand;
    private final ModelPart baseplate;

    public StatueModel(ModelPart root) {
        super(root);
        this.baseplate = root.getChild("baseplate");
        this.stand = this.baseplate.getChild("stand");

        HumanoidModelAccessor access = (HumanoidModelAccessor) this;
        access.setBody(this.stand.getChild("body"));
        access.setHead(this.body.getChild("head"));
        access.setLeftArm(this.body.getChild("leftArm"));
        access.setRightArm(this.body.getChild("rightArm"));
    }

    public static LayerDefinition createLayerDefinition() {
        MeshDefinition meshDefinition = HumanoidModel.createMesh(CubeDeformation.NONE, 0.0F);
        PartDefinition root = meshDefinition.getRoot();
        PartDefinition baseplate = root.addOrReplaceChild("baseplate", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, -2.0F, -5.0F, 10.0F, 2.0F, 10.0F, false), PartPose.offsetAndRotation(0.0F, 24.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        PartDefinition stand = baseplate.addOrReplaceChild("stand", CubeListBuilder.create().texOffs(0, 28).addBox(-4.0F, -12.0F, -2.0F, 8.0F, 12.0F, 4.0F, false), PartPose.offsetAndRotation(0.0F, -2.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        PartDefinition body = stand.addOrReplaceChild("body", CubeListBuilder.create().texOffs(24, 28).addBox(-4.0F, -10.0F, -2.0F, 8.0F, 10.0F, 4.0F, false), PartPose.offsetAndRotation(0.0F, -12.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        body.addOrReplaceChild("rightArm", CubeListBuilder.create().texOffs(20, 42).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, false), PartPose.offsetAndRotation(-5.0F, -8.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        body.addOrReplaceChild("leftArm", CubeListBuilder.create().texOffs(32, 12).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, false), PartPose.offsetAndRotation(5.0F, -8.0F, 0.0F, 0.0F, 0.0F, 0.0F));
        body.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 12).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, false), PartPose.offsetAndRotation(0.0F, -10.0F, 0.0F, 0.0F, 0.0F, 0.0F));
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
    protected Iterable<ModelPart> headParts() {
        return Collections.emptySet();
    }

    @Override
    protected Iterable<ModelPart> bodyParts() {
        return ImmutableList.of(this.baseplate);
    }
}