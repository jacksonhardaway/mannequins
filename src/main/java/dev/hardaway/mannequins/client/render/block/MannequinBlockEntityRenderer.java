package dev.hardaway.mannequins.client.render.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import dev.hardaway.mannequins.common.block.DummyBlock;
import dev.hardaway.mannequins.common.block.entity.DummyBlockEntity;
import dev.hardaway.mannequins.common.entity.ClientDummy;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.properties.RotationSegment;
import net.minecraft.world.phys.AABB;

public class MannequinBlockEntityRenderer implements BlockEntityRenderer<DummyBlockEntity> {

    private final BlockEntityRendererProvider.Context context;

    public MannequinBlockEntityRenderer(BlockEntityRendererProvider.Context context) {
        this.context = context;
    }

    @Override
    public void render(DummyBlockEntity blockEntity, float partialTick, PoseStack stack, MultiBufferSource buffer, int light, int overlay) {
        ClientDummy dummy = blockEntity.getDummy();
        if (dummy == null)
            return;

        stack.pushPose();
        stack.translate(0.5, 0.125, 0.5);
        stack.mulPose(Axis.YP.rotationDegrees(-RotationSegment.convertToDegrees(blockEntity.getBlockState().getValue(DummyBlock.ROTATION))));
        this.context.getEntityRenderer().render(dummy, 0, 0, 0, 0, partialTick, stack, buffer, light);
        stack.popPose();
    }

    @Override
    public AABB getRenderBoundingBox(DummyBlockEntity blockEntity) {
        BlockPos pos = blockEntity.getBlockPos();
        return AABB.encapsulatingFullBlocks(pos, pos.offset(0, 1, 0));
    }
}