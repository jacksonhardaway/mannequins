package dev.hardaway.mannequins.common.block;

import com.mojang.serialization.MapCodec;
import dev.hardaway.mannequins.common.block.entity.MannequinBlockEntity;
import dev.hardaway.mannequins.core.Mannequins;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static net.minecraft.world.level.block.ShulkerBoxBlock.CONTENTS;

public class MannequinBlock extends BaseEntityBlock implements SimpleWaterloggedBlock {
    public static final MapCodec<MannequinBlock> CODEC = simpleCodec(MannequinBlock::new);
    public static final EnumProperty<DoubleBlockHalf> HALF = BlockStateProperties.DOUBLE_BLOCK_HALF;
    public static final BooleanProperty WATERLOGGED = BlockStateProperties.WATERLOGGED;
    public static final IntegerProperty ROTATION = BlockStateProperties.ROTATION_16;

    public static final ResourceLocation MANNEQUIN_INVENTORY = Mannequins.path("mannequin_inventory");

    private static final VoxelShape SHAPE = Shapes.join(Shapes.empty(), Shapes.box(0.125, 0, 0.125, 0.875, 1, 0.875), BooleanOp.OR);
    private static final VoxelShape LOWER_SHAPE = Shapes.join(Shapes.empty(), Shapes.box(0.125, 0, 0.125, 0.875, 2, 0.875), BooleanOp.OR);
    private static final VoxelShape UPPER_SHAPE = Shapes.join(Shapes.empty(), Shapes.box(0.125, -1, 0.125, 0.875, 1, 0.875), BooleanOp.OR);


    public MannequinBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.defaultBlockState().setValue(ROTATION, 0).setValue(WATERLOGGED, false).setValue(HALF, DoubleBlockHalf.LOWER));
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return CODEC;
    }

    @Nullable
    private MannequinBlockEntity getMannequin(Level level, BlockState state, BlockPos pos) {
        BlockEntity be = level.getBlockEntity(state.getValue(HALF) == DoubleBlockHalf.LOWER ? pos : pos.below());
        return be instanceof MannequinBlockEntity mannequin ? mannequin : null;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return state.getValue(HALF) == DoubleBlockHalf.LOWER ? new MannequinBlockEntity(pos, state) : null;
    }

    @Override
    protected MenuProvider getMenuProvider(BlockState state, Level level, BlockPos pos) {
        return this.getMannequin(level, state, pos);
    }

    @Override
    protected boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockPos belowPos = pos.below();
        BlockState belowState = level.getBlockState(belowPos);
        return state.getValue(HALF) == DoubleBlockHalf.LOWER || belowState.is(this);
    }

    @Override
    protected BlockState updateShape(BlockState state, Direction facing, BlockState facingState, LevelAccessor level, BlockPos currentPos, BlockPos facingPos) {
        DoubleBlockHalf half = state.getValue(HALF);
        if (facing.getAxis() != Direction.Axis.Y || half == DoubleBlockHalf.LOWER != (facing == Direction.UP)) {
            return half == DoubleBlockHalf.LOWER && facing == Direction.DOWN && !state.canSurvive(level, currentPos)
                    ? Blocks.AIR.defaultBlockState()
                    : super.updateShape(state, facing, facingState, level, currentPos, facingPos);
        } else {
            return facingState.is(this) && facingState.getValue(HALF) != half
                    ? facingState.setValue(HALF, half)
                    : Blocks.AIR.defaultBlockState();
        }
    }

    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        BlockPos blockpos = context.getClickedPos();
        Level level = context.getLevel();
        if (blockpos.getY() < level.getMaxBuildHeight() - 1 && level.getBlockState(blockpos.above()).canBeReplaced(context)) {
            return this.defaultBlockState()
                    .setValue(ROTATION, RotationSegment.convertToSegment(context.getRotation() + 180.0F))
                    .setValue(HALF, DoubleBlockHalf.LOWER);
        }

        return null;
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity entity, ItemStack stack) {
        BlockPos above = pos.above();
        level.setBlock(above, DoublePlantBlock.copyWaterloggedFrom(level, above, state.setValue(HALF, DoubleBlockHalf.UPPER)), Block.UPDATE_ALL);
    }

    @Override
    protected BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(ROTATION, rotation.rotate(state.getValue(ROTATION), 16));
    }

    @Override
    protected BlockState mirror(BlockState state, Mirror mirror) {
        return state.setValue(ROTATION, mirror.mirror(state.getValue(ROTATION), 16));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(ROTATION, WATERLOGGED, HALF);
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hitResult) {
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        }

        MannequinBlockEntity mannequin = this.getMannequin(level, state, pos);
        if (mannequin != null) {
            player.openMenu(mannequin);
        }
        return InteractionResult.CONSUME;
    }

    @Override
    public BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
        if (!level.isClientSide) {
            if (player.isCreative()) {
                DoubleBlockHalf doubleblockhalf = state.getValue(HALF);
                if (doubleblockhalf == DoubleBlockHalf.UPPER) {
                    BlockPos blockpos = pos.below();
                    BlockState blockstate = level.getBlockState(blockpos);
                    if (blockstate.is(state.getBlock()) && blockstate.getValue(HALF) == DoubleBlockHalf.LOWER) {
                        BlockState blockstate1 = blockstate.getFluidState().is(Fluids.WATER) ? Blocks.WATER.defaultBlockState() : Blocks.AIR.defaultBlockState();
                        level.setBlock(blockpos, blockstate1, 35);
                        level.levelEvent(player, 2001, blockpos, Block.getId(blockstate));
                    }
                }
            } else {
                dropResources(state, level, pos, this.getMannequin(level, state, pos), player, player.getMainHandItem());
            }
        }

        return super.playerWillDestroy(level, pos, state, player);
    }

    @Override
    public void playerDestroy(Level level, Player player, BlockPos pos, BlockState state, @javax.annotation.Nullable BlockEntity te, ItemStack stack) {
        super.playerDestroy(level, player, pos, Blocks.AIR.defaultBlockState(), te, stack);
    }

    @Override
    protected List<ItemStack> getDrops(BlockState state, LootParams.Builder params) {
        BlockEntity be = params.getOptionalParameter(LootContextParams.BLOCK_ENTITY);
        if (be instanceof MannequinBlockEntity mannequin) {
            params = params.withDynamicDrop(MANNEQUIN_INVENTORY, p_56219_ -> {
                for (int i = 0; i < mannequin.getInventory().getSlots(); i++) {
                    p_56219_.accept(mannequin.getInventory().getStackInSlot(i));
                }
            });
        }

        return super.getDrops(state, params);
    }


    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return state.getValue(HALF) == DoubleBlockHalf.LOWER ? LOWER_SHAPE : UPPER_SHAPE;
    }

    @Override
    protected VoxelShape getCollisionShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }
}
