package dev.hardaway.mannequins.client.render.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import dev.hardaway.mannequins.common.block.MannequinBlock;
import dev.hardaway.mannequins.common.block.entity.MannequinBlockEntity;
import dev.hardaway.mannequins.common.entity.Dummy;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.properties.RotationSegment;
import net.minecraft.world.phys.AABB;

public class MannequinBlockEntityRenderer implements BlockEntityRenderer<MannequinBlockEntity> {

    private final BlockEntityRendererProvider.Context context;

    public MannequinBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        this.context = context;
    }

    @Override
    public void render(MannequinBlockEntity blockEntity, float partialTick, PoseStack stack, MultiBufferSource buffer, int light, int overlay) {
        Dummy dummy = blockEntity.getDummy();
        if (dummy == null)
            return;

        stack.pushPose();
        stack.translate(0.5, 0.0625, 0.5);
        stack.mulPose(Axis.YP.rotationDegrees(-RotationSegment.convertToDegrees(blockEntity.getBlockState().getValue(MannequinBlock.ROTATION))));
        this.context.getEntityRenderer().render(dummy, 0, 0, 0, 0, partialTick, stack, buffer, light);
        stack.popPose();
    }

    @Override
    public AABB getRenderBoundingBox(MannequinBlockEntity blockEntity) {
        BlockPos pos = blockEntity.getBlockPos();
        return AABB.encapsulatingFullBlocks(pos, pos.offset(0, 1, 0));
    }
}