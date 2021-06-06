package me.jaackson.mannequins.client.render.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import me.jaackson.mannequins.common.entity.Mannequin;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.HumanoidArm;

import java.util.Collections;

/**
 * @author Echolite, Jackson, Ocelot
 */
public class MannequinFullModel extends MannequinModel {
    private final ModelPart stand;
    private final ModelPart baseplate;

    public MannequinFullModel() {
        this(0.0F);
    }

    public MannequinFullModel(float inflate) {
        super(inflate, 64, 64);
        this.baseplate = new ModelPart(this);
        this.baseplate.setPos(0.0F, 24.0F, 0.0F);
        this.baseplate.texOffs(24, 52).addBox(-5.0F, -2.0F, -5.0F, 10.0F, 2.0F, 10.0F, 0.0F, false);

        this.stand = new ModelPart(this);
        this.stand.setPos(0.0F, -2.0F, 0.0F);
        this.baseplate.addChild(this.stand);
        this.stand.texOffs(0, 50).addBox(-1.0F, -12.0F, -1.0F, 2.0F, 12.0F, 2.0F, 0.0F, false);

        this.body = new ModelPart(this);
        this.body.setPos(0.0F, -12.0F, 0.0F);
        this.stand.addChild(this.body);
        this.body.texOffs(0, 16).addBox(-4.0F, -10.0F, -2.0F, 8.0F, 10.0F, 4.0F, 0.0F, false);

        this.rightArm = new ModelPart(this);
        this.rightArm.setPos(-5.0F, -8.0F, 0.0F);
        this.body.addChild(this.rightArm);
        this.rightArm.texOffs(32, 0).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);

        this.leftArm = new ModelPart(this);
        this.leftArm.setPos(5.0F, -8.0F, 0.0F);
        this.body.addChild(this.leftArm);
        this.leftArm.texOffs(48, 0).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);

        this.head = new ModelPart(this);
        this.head.setPos(0.0F, -10.0F, 0.0F);
        this.body.addChild(this.head);
        this.head.texOffs(0, 0).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.0F, false);

        this.hat.visible = false;
        this.leftLeg.visible = false;
        this.rightLeg.visible = false;
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
        this.head.translateAndRotate(poseStack);
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