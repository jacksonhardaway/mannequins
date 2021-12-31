package gg.moonflower.mannequins.client.render.model;

import com.mojang.blaze3d.vertex.PoseStack;

public interface TranslatedMannequin {
    void translateToHead(PoseStack poseStack);

    void translateToBody(PoseStack poseStack);

    void translateToElytra(PoseStack poseStack);
}
