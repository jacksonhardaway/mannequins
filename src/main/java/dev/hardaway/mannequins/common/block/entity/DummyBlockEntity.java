package dev.hardaway.mannequins.common.block.entity;

import com.mojang.datafixers.util.Pair;
import com.mojang.logging.LogUtils;
import dev.hardaway.mannequins.api.DummyExpression;
import dev.hardaway.mannequins.api.DummyPose;
import dev.hardaway.mannequins.common.block.DummyBlock;
import dev.hardaway.mannequins.common.compat.vanity.MannequinsVanityCompat;
import dev.hardaway.mannequins.common.entity.ClientDummy;
import dev.hardaway.mannequins.common.menu.DummyInventory;
import dev.hardaway.mannequins.core.registry.MannequinsComponents;
import dev.hardaway.mannequins.core.registry.MannequinsRegistries;
import dev.hardaway.mannequins.core.util.BuiltinPoses;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.resources.RegistryFixedCodec;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.Nameable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import tech.thatgravyboat.vanity.common.registries.ModDataComponents;

import java.util.Objects;

public abstract class DummyBlockEntity extends BlockEntity implements MenuProvider, Nameable {
    private static final Logger LOGGER = LogUtils.getLogger();
    public static final DummyPose DEFAULT_POSE = BuiltinPoses.DEFAULT.getPose();

    private final DummyInventory inventory = new DummyInventory();

    private DummyPose pose = DEFAULT_POSE;
    private BuiltinPoses lastPoseCycle = BuiltinPoses.DEFAULT;

    private @Nullable Pair<ResourceLocation, String> vanity;
    private @Nullable Holder<DummyExpression> expression;
    private @Nullable ClientDummy dummy;
    private @Nullable Component name;

    public DummyBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
    }

    @Override
    public void setLevel(Level level) {
        super.setLevel(level);
        if (level.isClientSide()) {
            this.setDummy(new ClientDummy(level, this));
        }
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        if (this.level.isClientSide()) {
            this.getDummy().setRemoved(Entity.RemovalReason.DISCARDED);
        }
    }

    @Override
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return ClientboundBlockEntityDataPacket.create(this);
    }

    @Override
    public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
        return this.saveCustomOnly(registries);
    }

    @Override
    protected void loadAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        if (tag.contains("CustomName", 8)) {
            this.name = parseCustomNameSafe(tag.getString("CustomName"), registries);
        }

        DummyPose.CODEC.parse(NbtOps.INSTANCE, tag.getCompound("mannequin_pose"))
                .resultOrPartial(LOGGER::error)
                .ifPresent(this::setPose);

        if (tag.contains("mannequin_expression", CompoundTag.TAG_STRING)) {
            RegistryFixedCodec.create(MannequinsRegistries.EXPRESSIONS).parse(RegistryOps.create(NbtOps.INSTANCE, registries), tag.get("mannequin_expression"))
                    .resultOrPartial(LOGGER::error)
                    .ifPresent(this::setExpression);
        } else {
            this.setExpression(null);
        }

        this.inventory.deserializeNBT(registries, tag.getCompound("inventory"));

        if (MannequinsVanityCompat.isLoaded() && tag.contains("vanity", CompoundTag.TAG_COMPOUND)) {
            Objects.requireNonNull(ModDataComponents.STYLE.get().codec())
                    .parse(NbtOps.INSTANCE, tag.getCompound("vanity"))
                    .resultOrPartial(LOGGER::error)
                    .ifPresent(vanity -> this.vanity = vanity);
        } else {
            this.vanity = null;
        }

        super.loadAdditional(tag, registries);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        if (this.name != null) {
            tag.putString("CustomName", Component.Serializer.toJson(this.name, registries));
        }

        DummyPose.CODEC
                .encodeStart(NbtOps.INSTANCE, this.pose)
                .resultOrPartial(LOGGER::error)
                .ifPresent(poseTag -> tag.put("mannequin_pose", poseTag));

        if (this.expression != null) {
            RegistryFixedCodec.create(MannequinsRegistries.EXPRESSIONS)
                    .encodeStart(RegistryOps.create(NbtOps.INSTANCE, registries), this.expression)
                    .resultOrPartial(LOGGER::error)
                    .ifPresent(expressionTag -> tag.put("mannequin_expression", expressionTag));
        }

        tag.put("inventory", this.inventory.serializeNBT(registries));

        if (MannequinsVanityCompat.isLoaded() && this.vanity != null) {
            Objects.requireNonNull(ModDataComponents.STYLE.get().codec())
                    .encodeStart(NbtOps.INSTANCE, this.vanity)
                    .resultOrPartial(LOGGER::error)
                    .ifPresent(vanityTag -> tag.put("vanity", vanityTag));
        }

        super.saveAdditional(tag, registries);
    }

    @Override
    protected void applyImplicitComponents(BlockEntity.DataComponentInput componentInput) {
        this.name = componentInput.get(DataComponents.CUSTOM_NAME);
        this.pose = componentInput.getOrDefault(MannequinsComponents.POSE, DEFAULT_POSE);
        this.expression = componentInput.get(MannequinsComponents.EXPRESSION);

        if (MannequinsVanityCompat.isLoaded()) {
            this.vanity = componentInput.get(ModDataComponents.STYLE);
        }
    }

    @Override
    protected void collectImplicitComponents(DataComponentMap.Builder components) {
        components.set(DataComponents.CUSTOM_NAME, this.name);
        components.set(MannequinsComponents.POSE, this.pose);
        components.set(MannequinsComponents.EXPRESSION, this.expression);
        if (MannequinsVanityCompat.isLoaded()) {
            components.set(ModDataComponents.STYLE, this.vanity);
        }
    }

    @Override
    public void removeComponentsFromTag(CompoundTag tag) {
        tag.remove("CustomName");
        tag.remove("mannequin_pose");
        tag.remove("mannequin_expression");
        tag.remove("vanity");
    }

    public void markDirty() {
        BlockPos pos = this.getBlockPos();
        BlockState state = this.getBlockState();

        if (this.level != null) {
            this.setChanged();
            this.level.sendBlockUpdated(pos, state, state, 3);
            this.level.gameEvent(GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(state));
        }
    }

    public @Nullable Pair<ResourceLocation, String> getVanity() {
        return vanity;
    }

    @Nullable
    public ClientDummy getDummy() {
        return dummy;
    }

    public void setDummy(@Nullable ClientDummy dummy) {
        if (!this.level.isClientSide())
            return;

        this.dummy = dummy;
    }

    public @Nullable Holder<DummyExpression> getExpression() {
        return expression;
    }

    public void setExpression(@Nullable Holder<DummyExpression> expression) {
        this.expression = expression;
        this.markDirty();
    }

    public BuiltinPoses getLastPoseCycle() {
        return lastPoseCycle;
    }

    public void setLastPoseCycle(BuiltinPoses lastPoseCycle) {
        this.lastPoseCycle = lastPoseCycle;
    }

    public DummyPose getPose() {
        return pose;
    }

    public void setPose(DummyPose pose) {
        this.pose = pose;
        this.markDirty();
    }

    public void randomizePose() {
        int nextOrdinal = this.getLastPoseCycle().ordinal() + 1;
        if (nextOrdinal >= BuiltinPoses.VALUES.length) {
            nextOrdinal = 0;
        }

        BuiltinPoses pose = BuiltinPoses.VALUES[nextOrdinal];
        this.setLastPoseCycle(pose);
        this.setPose(pose.getPose());
    }

    public void resetPose() {
        this.setExpression(null);
        this.setPose(BuiltinPoses.DEFAULT.getPose());
        this.setLastPoseCycle(BuiltinPoses.DEFAULT);
    }

    public DummyInventory getInventory() {
        return inventory;
    }

    @Override
    public Component getName() {
        return this.name != null ? this.name : this.getBlockState().getBlock().getName();
    }

    @Override
    public Component getDisplayName() {
        return this.getName();
    }

    @Nullable
    @Override
    public Component getCustomName() {
        return this.name;
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
        return ((DummyBlock) this.getBlockState().getBlock()).createMenu(containerId, playerInventory, ContainerLevelAccess.create(this.getLevel(), this.getBlockPos()), this);
    }
}
