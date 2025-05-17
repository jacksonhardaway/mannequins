package dev.hardaway.mannequins.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.datafixers.util.Pair;
import dev.hardaway.mannequins.api.DummyExpression;
import dev.hardaway.mannequins.client.block.MannequinClientBlockExtension;
import dev.hardaway.mannequins.client.model.DummyModel;
import dev.hardaway.mannequins.common.block.DummyBlock;
import dev.hardaway.mannequins.common.block.entity.DummyBlockEntity;
import dev.hardaway.mannequins.common.compat.vanity.MannequinsAssetTypes;
import dev.hardaway.mannequins.common.compat.vanity.MannequinsVanityCompat;
import dev.hardaway.mannequins.common.entity.ClientDummy;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.layers.CustomHeadLayer;
import net.minecraft.client.renderer.entity.layers.ElytraLayer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.client.extensions.common.IClientBlockExtensions;
import tech.thatgravyboat.vanity.api.style.Style;
import tech.thatgravyboat.vanity.client.design.ClientDesignManager;

import java.util.HashMap;
import java.util.Map;

public class DummyEntityRenderer extends LivingEntityRenderer<ClientDummy, DummyModel> {

    private static final Map<Block, DummyModel> MODEL_PROVIDERS = new HashMap<>();
    private final EntityRendererProvider.Context context;

    public DummyEntityRenderer(EntityRendererProvider.Context context, DummyModel model, DummyModel innerArmorModel, DummyModel outerArmorModel) {
        super(context, model, 0.0F);
        this.addLayer(new HumanoidArmorLayer<>(this, innerArmorModel, outerArmorModel, context.getModelManager()));
        this.addLayer(new ElytraLayer<>(this, context.getModelSet()));
        this.addLayer(new CustomHeadLayer<>(this, context.getModelSet(), context.getItemInHandRenderer()));
        this.addLayer(new ItemInHandLayer<>(this, context.getItemInHandRenderer()));
        this.context = context;
        MODEL_PROVIDERS.clear();
    }

    @Override
    protected boolean shouldShowName(ClientDummy entity) {
        return false;
    }

    @Override
    public void render(ClientDummy entity, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        this.model = MODEL_PROVIDERS.computeIfAbsent(entity.getDummy().getBlockState().getBlock(), block -> ((MannequinClientBlockExtension) IClientBlockExtensions.of(block)).getRenderingDelegate().apply(this.context));
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(ClientDummy entity) {
        DummyBlockEntity mannequin = entity.getDummy();
        if (MannequinsVanityCompat.isLoaded()) {
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

        Holder<DummyExpression> expression = mannequin.getExpression();
        if (expression != null) {
            return expression.value().asset().withPrefix("textures/").withSuffix(".png");
        }

        return ((DummyBlock) mannequin.getBlockState().getBlock()).getDummyTexture();
    }
}
