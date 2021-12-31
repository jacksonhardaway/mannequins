package gg.moonflower.mannequins.common.entity;

import gg.moonflower.mannequins.client.screen.AbstractMannequinScreen;
import gg.moonflower.mannequins.client.screen.StoneMannequinScreen;
import gg.moonflower.mannequins.common.menu.MannequinInventoryMenu;
import gg.moonflower.mannequins.core.registry.MannequinsItems;
import gg.moonflower.mannequins.core.registry.MannequinsSounds;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.PickaxeItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

import java.util.Objects;

public class StoneMannequin extends AbstractMannequin {
    public StoneMannequin(EntityType<? extends AbstractMannequin> type, Level level) {
        super(type, level);
    }

    @Override
    public boolean canBreak(DamageSource source, Entity entity) {
        if (!super.canBreak(source, entity))
            return false;

        Player player = ((Player) Objects.requireNonNull(source.getDirectEntity())); // Player isn't null because it's checked in the super.
        if (source.isCreativePlayer()) {
            return true;
        } else {
            return player.getItemInHand(InteractionHand.MAIN_HAND).getItem() instanceof PickaxeItem;
        }
    }

    @Override
    public ItemStack getItem() {
        return new ItemStack(MannequinsItems.STONE_MANNEQUIN.get());
    }

    @Override
    public ParticleOptions getParticle() {
        return new BlockParticleOption(ParticleTypes.BLOCK, Blocks.STONE.defaultBlockState());
    }

    @Override
    public SoundEvent getHitSound() {
        return MannequinsSounds.ENTITY_STONE_MANNEQUIN_HIT.get();
    }

    @Override
    public SoundEvent getBrokenSound() {
        return MannequinsSounds.ENTITY_STONE_MANNEQUIN_BREAK.get();
    }

    @Override
    public SoundEvent getPlaceSound() {
        return MannequinsSounds.ENTITY_STONE_MANNEQUIN_PLACE.get();
    }

    @Override
    public Fallsounds getFallSounds() {
        return new Fallsounds(MannequinsSounds.ENTITY_STONE_MANNEQUIN_FALL.get(), MannequinsSounds.ENTITY_STONE_MANNEQUIN_FALL.get());
    }

    @Override
    public AbstractMannequinScreen getScreen(MannequinInventoryMenu menu, Inventory inventory) {
        return new StoneMannequinScreen(menu, inventory, this);
    }

}
