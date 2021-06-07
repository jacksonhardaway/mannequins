package me.jaackson.mannequins.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import me.jaackson.mannequins.client.render.model.MannequinFullModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CustomHeadLayer.class)
public abstract class CustomHeadLayerMixin<T extends LivingEntity, M extends EntityModel<T> & HeadedModel> extends RenderLayer<T, M> {

    private CustomHeadLayerMixin(RenderLayerParent<T, M> renderLayerParent) {
        super(renderLayerParent);
    }

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/geom/ModelPart;translateAndRotate(Lcom/mojang/blaze3d/vertex/PoseStack;)V", shift = At.Shift.BEFORE))
    public void translateMannequinHead(PoseStack poseStack, MultiBufferSource multiBufferSource, int i, T livingEntity, float f, float g, float h, float j, float k, float l, CallbackInfo ci) {
        if (this.getParentModel() instanceof MannequinFullModel) {
            ((MannequinFullModel) this.getParentModel()).translateToHead(poseStack);
        }
    }
}
