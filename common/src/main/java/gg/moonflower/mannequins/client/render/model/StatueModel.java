package gg.moonflower.mannequins.client.render.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import gg.moonflower.mannequins.common.entity.Statue;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.world.entity.HumanoidArm;

import java.util.Collections;

/**
 * @author Echolite
 */
public class StatueModel extends BasicMannequinModel<Statue> implements TranslatedMannequin {
    private final ModelPart stand;
    private final ModelPart baseplate;

    public StatueModel() {
        this(0.0F);
    }

    public StatueModel(float inflate) {
        super(inflate, 64, 64);
        this.baseplate = new ModelPart(this);
        this.baseplate.setPos(0.0F, 24.0F, 0.0F);
        this.baseplate.texOffs(0, 0).addBox(-5.0F, -2.0F, -5.0F, 10.0F, 2.0F, 10.0F, 0.0F, false);

        this.stand = new ModelPart(this);
        this.stand.setPos(0.0F, -2.0F, 0.0F);
        this.baseplate.addChild(this.stand);
        this.stand.texOffs(0, 28).addBox(-4.0F, -12.0F, -2.0F, 8.0F, 12.0F, 4.0F, 0.0F, false);

        this.body = new ModelPart(this);
        this.body.setPos(0.0F, -12.0F, 0.0F);
        this.stand.addChild(this.body);
        this.body.texOffs(24, 28).addBox(-4.0F, -10.0F, -2.0F, 8.0F, 10.0F, 4.0F, 0.0F, false);

        this.rightArm = new ModelPart(this);
        this.rightArm.setPos(-5.0F, -8.0F, 0.0F);
        this.body.addChild(this.rightArm);
        this.rightArm.texOffs(20, 42).addBox(-3.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);

        this.leftArm = new ModelPart(this);
        this.leftArm.setPos(5.0F, -8.0F, 0.0F);
        this.body.addChild(this.leftArm);
        this.leftArm.texOffs(32, 12).addBox(-1.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, 0.0F, false);

        this.head = new ModelPart(this);
        this.head.setPos(0.0F, -10.0F, 0.0F);
        this.body.addChild(this.head);
        this.head.texOffs(0, 12).addBox(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, 0.0F, false);

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