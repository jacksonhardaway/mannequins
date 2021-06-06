package me.jaackson.mannequins.common.entity;

import me.jaackson.mannequins.Mannequins;
import me.jaackson.mannequins.bridge.NetworkBridge;
import me.jaackson.mannequins.bridge.PlayerBridge;
import me.jaackson.mannequins.common.menu.MannequinInventoryMenu;
import me.jaackson.mannequins.common.network.ClientboundAttackMannequin;
import me.jaackson.mannequins.common.network.ClientboundOpenMannequinScreen;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.Rotations;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.vehicle.AbstractMinecart;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

/**
 * @author Jackson, Ocelot
 */
public class Mannequin extends LivingEntity {
    public static final EntityDataAccessor<Rotations> DATA_HEAD_POSE = SynchedEntityData.defineId(Mannequin.class, EntityDataSerializers.ROTATIONS);
    public static final EntityDataAccessor<Rotations> DATA_BODY_POSE = SynchedEntityData.defineId(Mannequin.class, EntityDataSerializers.ROTATIONS);
    public static final EntityDataAccessor<Rotations> DATA_LEFT_ARM_POSE = SynchedEntityData.defineId(Mannequin.class, EntityDataSerializers.ROTATIONS);
    public static final EntityDataAccessor<Rotations> DATA_RIGHT_ARM_POSE = SynchedEntityData.defineId(Mannequin.class, EntityDataSerializers.ROTATIONS);
    public static final EntityDataAccessor<Boolean> DATA_DISABLED = SynchedEntityData.defineId(Mannequin.class, EntityDataSerializers.BOOLEAN);
    private static final Predicate<Entity> MINECART = (entity) -> entity instanceof AbstractMinecart && ((AbstractMinecart) entity).getMinecartType() == AbstractMinecart.Type.RIDEABLE;
    private static final Rotations DEFAULT_HEAD_POSE = new Rotations(0.0F, 0.0F, 0.0F);
    private static final Rotations DEFAULT_BODY_POSE = new Rotations(0.0F, 0.0F, 0.0F);
    private static final Rotations DEFAULT_LEFT_ARM_POSE = new Rotations(-10.0F, 0.0F, -10.0F);
    private static final Rotations DEFAULT_RIGHT_ARM_POSE = new Rotations(-10.0F, 0.0F, 10.0F);
    private final SimpleContainer inventory = new SimpleContainer(4);
    public long lastHit;
    private Rotations headPose = DEFAULT_HEAD_POSE;
    private Rotations bodyPose = DEFAULT_BODY_POSE;
    private Rotations leftArmPose = DEFAULT_LEFT_ARM_POSE;
    private Rotations rightArmPose = DEFAULT_RIGHT_ARM_POSE;
    private int attackAnimation;
    private float attackAnimationXFactor;
    private float attackAnimationZFactor;

    public Mannequin(EntityType<? extends Mannequin> type, Level level) {
        super(type, level);
        this.maxUpStep = 0.0F;
    }

    public void onAttack(float attackYaw) {
        float rotation = attackYaw - (float) (this.yBodyRot / 180.0 * Math.PI);
        this.hurtDir = attackYaw;
        this.attackAnimation = 40;
        this.attackAnimationXFactor = Mth.cos(rotation);
        this.attackAnimationZFactor = Mth.sin(rotation);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(DATA_HEAD_POSE, DEFAULT_HEAD_POSE);
        this.entityData.define(DATA_BODY_POSE, DEFAULT_BODY_POSE);
        this.entityData.define(DATA_LEFT_ARM_POSE, DEFAULT_LEFT_ARM_POSE);
        this.entityData.define(DATA_RIGHT_ARM_POSE, DEFAULT_RIGHT_ARM_POSE);
        this.entityData.define(DATA_DISABLED, false);
    }

    @Override
    public Iterable<ItemStack> getHandSlots() {
        return Collections.emptyList();
    }

    @Override
    public Iterable<ItemStack> getArmorSlots() {
        return Collections.emptyList();
    }

    @Override
    public ItemStack getItemBySlot(EquipmentSlot slot) {
        if (slot == null)
            return ItemStack.EMPTY;

        switch (slot) {
            case HEAD:
                return this.inventory.getItem(0);
            case CHEST:
                return this.inventory.getItem(1);
            case MAINHAND:
                return this.inventory.getItem(2);
            case OFFHAND:
                return this.inventory.getItem(3);
            default:
                return ItemStack.EMPTY;
        }
    }

    @Override
    public void setItemSlot(EquipmentSlot slot, ItemStack stack) {
        if (slot == null)
            return;

        switch (slot) {
            case HEAD:
                this.inventory.setItem(0, stack);
                break;
            case CHEST:
                this.inventory.setItem(1, stack);
                break;
            case MAINHAND:
                this.inventory.setItem(2, stack);
                break;
            case OFFHAND:
                this.inventory.setItem(3, stack);
                break;
        }
    }

    @Override
    public void addAdditionalSaveData(CompoundTag tag) {
        super.addAdditionalSaveData(tag);
        tag.put("Pose", this.writePose());
        tag.putBoolean("Disabled", this.isDisabled());

        ListTag listTag = new ListTag();
        for (int i = 2; i < this.inventory.getContainerSize(); ++i) {
            ItemStack itemStack = this.inventory.getItem(i);
            if (!itemStack.isEmpty()) {
                CompoundTag compoundTag2 = new CompoundTag();
                compoundTag2.putByte("Slot", (byte) i);
                itemStack.save(compoundTag2);
                listTag.add(compoundTag2);
            }
        }
        tag.put("Items", listTag);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag tag) {
        super.readAdditionalSaveData(tag);
        this.readPose(tag.getCompound("Pose"));
        this.entityData.set(DATA_DISABLED, tag.getBoolean("Disabled"));

        ListTag listTag = tag.getList("Items", 10);
        for (int i = 0; i < listTag.size(); ++i) {
            CompoundTag compoundTag2 = listTag.getCompound(i);
            int j = compoundTag2.getByte("Slot") & 255;
            if (j >= 2 && j < this.inventory.getContainerSize()) {
                this.inventory.setItem(j, ItemStack.of(compoundTag2));
            }
        }
    }

    private void readPose(CompoundTag tag) {
        ListTag headPose = tag.getList("Head", 5);
        this.setHeadPose(headPose.isEmpty() ? DEFAULT_HEAD_POSE : new Rotations(headPose));
        ListTag bodyPose = tag.getList("Body", 5);
        this.setBodyPose(bodyPose.isEmpty() ? DEFAULT_BODY_POSE : new Rotations(bodyPose));
        ListTag leftArmPose = tag.getList("LeftArm", 5);
        this.setLeftArmPose(leftArmPose.isEmpty() ? DEFAULT_LEFT_ARM_POSE : new Rotations(leftArmPose));
        ListTag rightArmPose = tag.getList("RightArm", 5);
        this.setRightArmPose(rightArmPose.isEmpty() ? DEFAULT_RIGHT_ARM_POSE : new Rotations(rightArmPose));
    }

    private CompoundTag writePose() {
        CompoundTag pose = new CompoundTag();
        if (!DEFAULT_HEAD_POSE.equals(this.headPose)) {
            pose.put("Head", this.headPose.save());
        }

        if (!DEFAULT_BODY_POSE.equals(this.bodyPose)) {
            pose.put("Body", this.bodyPose.save());
        }

        if (!DEFAULT_LEFT_ARM_POSE.equals(this.leftArmPose)) {
            pose.put("LeftArm", this.leftArmPose.save());
        }

        if (!DEFAULT_RIGHT_ARM_POSE.equals(this.rightArmPose)) {
            pose.put("RightArm", this.rightArmPose.save());
        }

        return pose;
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    protected void doPush(Entity entity) {
    }

    @Override
    protected void pushEntities() {
        List<Entity> list = this.level.getEntities(this, this.getBoundingBox(), MINECART);

        for (Entity entity : list) {
            if (this.distanceToSqr(entity) <= 0.2D) {
                entity.push(this);
            }
        }
    }

    @Override
    public InteractionResult interact(Player player, InteractionHand hand) {
        if (this.isDisabled())
            return InteractionResult.FAIL;
        if (!player.getItemInHand(hand).isEmpty() && !player.isSecondaryUseActive())
            return InteractionResult.PASS;
        if (this.level.isClientSide())
            return InteractionResult.SUCCESS;

        ServerPlayer serverPlayer = (ServerPlayer) player;
        if (serverPlayer.containerMenu != serverPlayer.inventoryMenu)
            serverPlayer.closeContainer();

        PlayerBridge.incrementContainerId(serverPlayer);
        NetworkBridge.sendClientbound(ClientboundOpenMannequinScreen.CHANNEL, serverPlayer, new ClientboundOpenMannequinScreen(PlayerBridge.getContainerId(serverPlayer), this.getId()));
        serverPlayer.containerMenu = new MannequinInventoryMenu(PlayerBridge.getContainerId(serverPlayer), serverPlayer.inventory, this.inventory, this);
        serverPlayer.containerMenu.addSlotListener(serverPlayer);
        // TODO: fire container event
        //        MinecraftForge.EVENT_BUS.post(new PlayerContainerEvent.Open(serverPlayer, serverPlayer.containerMenu));


        return InteractionResult.CONSUME;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (this.level.isClientSide() || !this.isAlive()) return false;

        if (DamageSource.OUT_OF_WORLD.equals(source)) {
            this.remove();
            return false;
        }

        if (this.isInvulnerableTo(source))
            return false;

        if (source.isExplosion()) {
            this.brokenByAnything(source);
            this.remove();
            return false;
        }

        if (DamageSource.IN_FIRE.equals(source)) {
            if (this.isOnFire()) {
                this.causeDamage(source, 0.15F);
            } else {
                this.setSecondsOnFire(5);
            }

            return false;
        }

        if (DamageSource.ON_FIRE.equals(source) && this.getHealth() > 0.5F) {
            this.causeDamage(source, 4.0F);
            return false;
        }

        Entity entity = source.getEntity();
        if (entity == null || !(source.getDirectEntity() instanceof Player) || !entity.isShiftKeyDown()) {
            Vec3 pos = source.getSourcePosition();
            if (pos == null)
                return false;
            this.hurtDir = (float) -Mth.atan2(pos.x() - this.getX(), pos.z() - this.getZ());
            this.level.broadcastEntityEvent(this, (byte) 32);
            NetworkBridge.sendClientboundTracking(ClientboundAttackMannequin.CHANNEL, this, new ClientboundAttackMannequin(this.getId(), this.hurtDir));
            ((ServerLevel) this.level).sendParticles(ParticleTypes.DAMAGE_INDICATOR, this.getX(), this.getEyeY(), this.getZ(), (int) ((double) amount * 0.5D), 0.1D, 0.0D, 0.1D, 0.2D);
            return true;
        }

        if (source.getEntity() instanceof Player && !((Player) source.getEntity()).abilities.mayBuild)
            return false;

        if (source.isCreativePlayer()) {
            this.playBrokenSound();
            this.showBreakingParticles();
            this.remove();
            return true;
        }

        long time = this.level.getGameTime();
        if (time - this.lastHit > 5L) {
            this.level.broadcastEntityEvent(this, (byte) 32);
            this.lastHit = time;
        } else {
            this.brokenByPlayer(source);
            this.showBreakingParticles();
            this.remove();
        }

        return true;
    }

    @Override
    @Environment(EnvType.CLIENT)
    public void handleEntityEvent(byte event) {
        if (event == 32) {
            if (this.level.isClientSide) {
                this.level.playLocalSound(this.getX(), this.getY(), this.getZ(), Mannequins.mannequinHitSound.get(), this.getSoundSource(), 0.3F, 1.0F, false);
                this.lastHit = this.level.getGameTime();
            }
        } else {
            super.handleEntityEvent(event);
        }
    }

    @Override
    @Environment(EnvType.CLIENT)
    public boolean shouldRenderAtSqrDistance(double distance) {
        double d0 = this.getBoundingBox().getSize() * 4.0D;
        if (Double.isNaN(d0) || d0 == 0.0D) {
            d0 = 4.0D;
        }

        d0 = d0 * 64.0D;
        return distance < d0 * d0;
    }

    private void showBreakingParticles() {
        if (this.level instanceof ServerLevel) {
            ((ServerLevel) this.level).sendParticles(new BlockParticleOption(ParticleTypes.BLOCK, Blocks.OAK_PLANKS.defaultBlockState()), this.getX(), this.getY(0.6666666666666666D), this.getZ(), 10, this.getBbWidth() / 4.0F, this.getBbHeight() / 4.0F, this.getBbWidth() / 4.0F, 0.05D);
        }
    }

    private void causeDamage(DamageSource source, float damage) {
        float f = this.getHealth();
        f = f - damage;
        if (f <= 0.5F) {
            this.brokenByAnything(source);
            this.remove();
        } else {
            this.setHealth(f);
        }
    }

    private void brokenByPlayer(DamageSource source) {
        Block.popResource(this.level, this.blockPosition(), new ItemStack(Mannequins.mannequinItem.get()));
        this.brokenByAnything(source);
    }

    private void brokenByAnything(DamageSource source) {
        this.playBrokenSound();
        this.dropAllDeathLoot(source);

        for (int i = 0; i < this.inventory.getContainerSize(); ++i) {
            ItemStack stack = this.inventory.getItem(i);
            if (!stack.isEmpty()) {
                Block.popResource(this.level, this.blockPosition().above(), stack);
                this.inventory.setItem(i, ItemStack.EMPTY);
            }
        }
    }

    private void playBrokenSound() {
        this.level.playSound(null, this.getX(), this.getY(), this.getZ(), Mannequins.mannequinBreakSound.get(), this.getSoundSource(), 1.0F, 1.0F);
    }

    @Override
    protected float tickHeadTurn(float p_110146_1_, float p_110146_2_) {
        this.yBodyRotO = this.yRotO;
        this.yBodyRot = this.yRot;
        return 0.0F;
    }

    @Override
    protected float getStandingEyeHeight(Pose pose, EntityDimensions dimensions) {
        return dimensions.height * 0.88F;
    }

    @Override
    public double getMyRidingOffset() {
        return 0.1F;
    }

    @Override
    public void setYBodyRot(float rot) {
        this.yBodyRotO = this.yRotO = rot;
        this.yHeadRotO = this.yHeadRot = rot;
    }

    @Override
    public void setYHeadRot(float rot) {
        this.yBodyRotO = this.yRotO = rot;
        this.yHeadRotO = this.yHeadRot = rot;
    }

    @Override
    public void tick() {
        super.tick();
        Rotations headPose = this.entityData.get(DATA_HEAD_POSE);
        if (!this.headPose.equals(headPose)) {
            this.setHeadPose(headPose);
        }

        Rotations bodyPose = this.entityData.get(DATA_BODY_POSE);
        if (!this.bodyPose.equals(bodyPose)) {
            this.setBodyPose(bodyPose);
        }

        Rotations leftArmPose = this.entityData.get(DATA_LEFT_ARM_POSE);
        if (!this.leftArmPose.equals(leftArmPose)) {
            this.setLeftArmPose(leftArmPose);
        }

        Rotations rightArmPose = this.entityData.get(DATA_RIGHT_ARM_POSE);
        if (!this.rightArmPose.equals(rightArmPose)) {
            this.setRightArmPose(rightArmPose);
        }

        if (this.level.isClientSide()) {
            if (this.attackAnimation <= 0)
                return;
            this.attackAnimation--;
        }
    }

    @Override
    public void kill() {
        this.remove();
    }

    // Graph: https://www.desmos.com/calculator/xkhzglfwkm
    private float getAttackAnimation(float partialTicks) {
        float x = this.attackAnimation - partialTicks;
        return Mth.cos(x) / 2F * Mth.sqrt(x) / (50 - x);
    }

    @Environment(EnvType.CLIENT)
    public boolean hasAnimation() {
        return this.attackAnimation > 0;
    }

    @Environment(EnvType.CLIENT)
    public float getAnimationRotationX(float partialTicks) {
        return this.attackAnimationXFactor * this.getAttackAnimation(partialTicks);
    }

    @Environment(EnvType.CLIENT)
    public float getAnimationRotationZ(float partialTicks) {
        return this.attackAnimationZFactor * this.getAttackAnimation(partialTicks);
    }

    public boolean isDisabled() {
        return this.entityData.get(DATA_DISABLED);
    }

    public Rotations getHeadPose() {
        return this.headPose;
    }

    public void setHeadPose(Rotations pose) {
        this.headPose = pose;
        this.entityData.set(DATA_HEAD_POSE, pose);
    }

    public Rotations getBodyPose() {
        return this.bodyPose;
    }

    public void setBodyPose(Rotations pose) {
        this.bodyPose = pose;
        this.entityData.set(DATA_BODY_POSE, pose);
    }

    public Rotations getLeftArmPose() {
        return this.leftArmPose;
    }

    public void setLeftArmPose(Rotations pose) {
        this.leftArmPose = pose;
        this.entityData.set(DATA_LEFT_ARM_POSE, pose);
    }

    public Rotations getRightArmPose() {
        return this.rightArmPose;
    }

    public void setRightArmPose(Rotations pose) {
        this.rightArmPose = pose;
        this.entityData.set(DATA_RIGHT_ARM_POSE, pose);
    }

    @Override
    public boolean skipAttackInteraction(Entity entity) {
        return entity instanceof Player && !this.level.mayInteract((Player) entity, this.blockPosition());
    }

    @Override
    public HumanoidArm getMainArm() {
        return HumanoidArm.RIGHT;
    }

    @Override
    protected SoundEvent getFallDamageSound(int damage) {
        return Mannequins.mannequinFallSound.get();
    }

    @Override
    @Nullable
    protected SoundEvent getHurtSound(DamageSource source) {
        return Mannequins.mannequinHitSound.get();
    }

    @Override
    @Nullable
    protected SoundEvent getDeathSound() {
        return Mannequins.mannequinBreakSound.get();
    }

    @Override
    public void thunderHit(ServerLevel level, LightningBolt lightning) {
    }

    @Override
    public boolean isAffectedByPotions() {
        return false;
    }

    @Override
    public boolean attackable() {
        return false;
    }
}