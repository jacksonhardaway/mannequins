package me.jaackson.mannequins.client.render.layer;

import com.mojang.authlib.GameProfile;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import me.jaackson.mannequins.client.render.model.MannequinFullModel;
import me.jaackson.mannequins.client.render.model.MannequinModel;
import me.jaackson.mannequins.common.entity.Mannequin;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.SkullBlockRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.AbstractSkullBlock;
import net.minecraft.world.level.block.entity.SkullBlockEntity;
import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

public class MannequinHeadLayer extends RenderLayer<Mannequin, MannequinModel> {
    public MannequinHeadLayer(RenderLayerParent<Mannequin, MannequinModel> renderLayerParent) {
        super(renderLayerParent);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffers, int i, Mannequin entity, float f, float g, float h, float j, float k, float l) {
        ItemStack stack = entity.getItemBySlot(EquipmentSlot.HEAD);
        if (!stack.isEmpty()) {
            Item item = stack.getItem();
            poseStack.pushPose();
            if (entity.isBaby()) {
                poseStack.translate(0.0D, 0.03125D, 0.0D);
                poseStack.scale(0.7F, 0.7F, 0.7F);
                poseStack.translate(0.0D, 1.0D, 0.0D);
            }

            if (this.getParentModel() instanceof MannequinFullModel) {
                ((MannequinFullModel) this.getParentModel()).translateToHead(poseStack);
            }
            if (item instanceof BlockItem && ((BlockItem) item).getBlock() instanceof AbstractSkullBlock) {
                poseStack.scale(1.1875F, -1.1875F, -1.1875F);

                // TODO: 1.17 update to new skull fetching
                GameProfile gameProfile = null;
                if (stack.hasTag()) {
                    CompoundTag tag = Objects.requireNonNull(stack.getTag());
                    if (tag.contains("SkullOwner", 10)) {
                        gameProfile = NbtUtils.readGameProfile(tag.getCompound("SkullOwner"));
                    } else if (tag.contains("SkullOwner", 8)) {
                        String string = tag.getString("SkullOwner");
                        if (!StringUtils.isBlank(string)) {
                            gameProfile = SkullBlockEntity.updateGameprofile(new GameProfile(null, string));
                            tag.put("SkullOwner", NbtUtils.writeGameProfile(new CompoundTag(), Objects.requireNonNull(gameProfile)));
                        }
                    }
                }

                poseStack.translate(-0.5D, 0.0D, -0.5D);
                SkullBlockRenderer.renderSkull(null, 180.0F, ((AbstractSkullBlock) ((BlockItem) item).getBlock()).getType(), gameProfile, f, poseStack, buffers, i);
            } else if (!(item instanceof ArmorItem) || ((ArmorItem) item).getSlot() != EquipmentSlot.HEAD) {
                poseStack.translate(0.0D, -0.25D, 0.0D);
                poseStack.mulPose(Vector3f.YP.rotationDegrees(180.0F));
                poseStack.scale(0.625F, -0.625F, -0.625F);

                Minecraft.getInstance().getItemInHandRenderer().renderItem(entity, stack, ItemTransforms.TransformType.HEAD, false, poseStack, buffers, i);
            }

            poseStack.popPose();
        }
    }
}