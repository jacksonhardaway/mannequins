package gg.moonflower.mannequins.core.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import gg.moonflower.mannequins.client.render.model.TranslatedMannequin;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.ElytraLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ElytraLayer.class)
public abstract class ElytraLayerMixin<T extends LivingEntity, M extends EntityModel<T>> extends RenderLayer<T, M> {

    private ElytraLayerMixin(RenderLayerParent<T, M> renderLayerParent) {
        super(renderLayerParent);
    }

    @Inject(method = "render(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;ILnet/minecraft/world/entity/LivingEntity;FFFFFF)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/model/EntityModel;copyPropertiesTo(Lnet/minecraft/client/model/EntityModel;)V", shift = At.Shift.AFTER))
    public void translateMannequinElytra(PoseStack poseStack, MultiBufferSource buffer, int packedLight, T entity, float f, float g, float h, float j, float k, float l, CallbackInfo ci) {
        if (this.getParentModel() instanceof TranslatedMannequin) {
            ((TranslatedMannequin) this.getParentModel()).translateToElytra(poseStack);
        }
    }
}
