package dev.hardaway.mannequins.client.model;

import com.mojang.blaze3d.vertex.PoseStack;

public interface TranslatedHumanoid {
    void translateToHead(PoseStack poseStack);

    void translateToBody(PoseStack poseStack);

    void translateToElytra(PoseStack poseStack);
}
