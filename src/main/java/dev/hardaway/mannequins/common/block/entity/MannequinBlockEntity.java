package dev.hardaway.mannequins.common.block.entity;

import com.mojang.logging.LogUtils;
import dev.hardaway.mannequins.common.component.MannequinPose;
import dev.hardaway.mannequins.common.entity.Dummy;
import dev.hardaway.mannequins.common.menu.MannequinInventory;
import dev.hardaway.mannequins.common.menu.MannequinMenu;
import dev.hardaway.mannequins.core.registry.MannequinsBlockEntities;
import dev.hardaway.mannequins.core.registry.MannequinsComponents;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Rotations;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.Nameable;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;

public class MannequinBlockEntity extends BlockEntity implements MenuProvider, Nameable {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final Rotations DEFAULT_HEAD_POSE = new Rotations(0.0F, 0.0F, 0.0F);
    private static final Rotations DEFAULT_BODY_POSE = new Rotations(0.0F, 0.0F, 0.0F);
    private static final Rotations DEFAULT_LEFT_ARM_POSE = new Rotations(-10.0F, 0.0F, -10.0F);
    private static final Rotations DEFAULT_RIGHT_ARM_POSE = new Rotations(-15.0F, 0.0F, 10.0F);
    public static final MannequinPose DEFAULT_POSE = new MannequinPose(DEFAULT_HEAD_POSE, DEFAULT_BODY_POSE, DEFAULT_LEFT_ARM_POSE, DEFAULT_RIGHT_ARM_POSE);

    private final MannequinInventory inventory = new MannequinInventory();

    private MannequinPose pose = DEFAULT_POSE;
    @Nullable
    private Dummy dummy;
    @Nullable
    private Component name;

    public MannequinBlockEntity(BlockPos pos, BlockState blockState) {
        super(MannequinsBlockEntities.MANNEQUIN.get(), pos, blockState);
    }

    @Override
    public void setLevel(Level level) {
        super.setLevel(level);
        if (level.isClientSide()) this.dummy = new Dummy(level, this.pose, this.inventory);
    }

    @Override
    public void setRemoved() {
        super.setRemoved();
        if (this.level.isClientSide()) this.dummy.setRemoved(Entity.RemovalReason.DISCARDED);
    }

    @Override
    public void writeClientSideData(AbstractContainerMenu menu, RegistryFriendlyByteBuf buffer) {
        MannequinPose.STREAM_CODEC.encode(buffer, this.pose);
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
        MannequinPose.CODEC.parse(NbtOps.INSTANCE, tag.getCompound("mannequin_pose"))
                .resultOrPartial(LOGGER::error)
                .ifPresent(this::setPose);

        this.inventory.deserializeNBT(registries, tag.getCompound("inventory"));
        if (tag.contains("CustomName", 8)) {
            this.name = parseCustomNameSafe(tag.getString("CustomName"), registries);
        }


        super.loadAdditional(tag, registries);
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider registries) {
        MannequinPose.CODEC.encodeStart(NbtOps.INSTANCE, this.pose)
                .resultOrPartial(LOGGER::error)
                .ifPresent(poseTag -> tag.put("mannequin_pose", poseTag));
        tag.put("inventory", this.inventory.serializeNBT(registries));

        if (this.name != null) {
            tag.putString("CustomName", Component.Serializer.toJson(this.name, registries));
        }

        super.saveAdditional(tag, registries);
    }

    @Override
    protected void applyImplicitComponents(BlockEntity.DataComponentInput componentInput) {
        this.name = componentInput.get(DataComponents.CUSTOM_NAME);
        this.pose = componentInput.getOrDefault(MannequinsComponents.MANNEQUIN_POSE, DEFAULT_POSE);
    }

    @Override
    protected void collectImplicitComponents(DataComponentMap.Builder components) {
        components.set(DataComponents.CUSTOM_NAME, this.name);
        components.set(MannequinsComponents.MANNEQUIN_POSE, this.pose);
    }

    @Override
    public void removeComponentsFromTag(CompoundTag tag) {
        tag.remove("CustomName");
        tag.remove("mannequin_pose");
    }

    public Dummy getDummy() {
        return dummy;
    }

    public MannequinPose getPose() {
        return pose;
    }

    public void setPose(MannequinPose pose) {
        this.pose = pose;
        if (this.dummy != null) this.dummy.setMannequinPose(pose);
    }

    public MannequinInventory getInventory() {
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
        return new MannequinMenu(containerId, playerInventory, this.inventory, ContainerLevelAccess.create(this.getLevel(), this.getBlockPos()), this.pose);
    }
}
