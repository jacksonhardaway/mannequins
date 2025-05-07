package dev.hardaway.mannequins.client.render.entity;

import com.mojang.datafixers.util.Pair;
import dev.hardaway.mannequins.api.MannequinExpression;
import dev.hardaway.mannequins.client.model.DummyModel;
import dev.hardaway.mannequins.common.block.entity.MannequinBlockEntity;
import dev.hardaway.mannequins.common.compat.vanity.MannequinsAssetTypes;
import dev.hardaway.mannequins.common.compat.vanity.MannequinsVanityCompat;
import dev.hardaway.mannequins.common.entity.Dummy;
import dev.hardaway.mannequins.core.Mannequins;
import dev.hardaway.mannequins.core.registry.MannequinsItems;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.client.renderer.entity.layers.ElytraLayer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import tech.thatgravyboat.vanity.api.style.Style;
import tech.thatgravyboat.vanity.client.design.ClientDesignManager;

public class DummyEntityRenderer extends LivingEntityRenderer<Dummy, DummyModel> {
    public DummyEntityRenderer(EntityRendererProvider.Context context, DummyModel model, DummyModel innerArmorModel, DummyModel outerArmorModel) {
        super(context, model, 0.0F);
        this.addLayer(new HumanoidArmorLayer<>(this, innerArmorModel, outerArmorModel, context.getModelManager()));
        this.addLayer(new ElytraLayer<>(this, context.getModelSet()));
        this.addLayer(new CustomHeadLayer<>(this, context.getModelSet(), context.getItemInHandRenderer()));
        this.addLayer(new ItemInHandLayer<>(this, context.getItemInHandRenderer()));
    }

    @Override
    protected boolean shouldShowName(Dummy entity) {
        return super.shouldShowName(entity) && (entity.shouldShowName() || entity.hasCustomName() && entity == this.entityRenderDispatcher.crosshairPickEntity);
    }

    @Override
    public ResourceLocation getTextureLocation(Dummy entity) {
        MannequinBlockEntity mannequin = entity.getMannequin();
        if (MannequinsVanityCompat.isActive()) {
            Pair<ResourceLocation, String> vanity = mannequin.getVanity();
            if (vanity != null) {
                // TODO: cache this result
                Style style = ClientDesignManager.INSTANCE.getDesign(vanity.getFirst())
                        .map((design) -> design.getStyleForItem(vanity.getSecond(), new ItemStack(mannequin.getBlockState().getBlock()))).orElse(null);

                ResourceLocation vanityTexture = (style != null ? style.asset(MannequinsAssetTypes.MANNEQUIN) : null);
                if (vanityTexture != null) {
                    return vanityTexture;
                }
            }
        }

        Holder<MannequinExpression> expression = mannequin.getExpression();
        if (expression != null) {
            return expression.value().asset().withPrefix("textures/").withSuffix(".png");
        }

        return Mannequins.path("textures/block/mannequin.png");
    }
}
