package me.jaackson.mannequins;

import me.jaackson.mannequins.bridge.NetworkBridge;
import me.jaackson.mannequins.bridge.RegistryBridge;
import me.jaackson.mannequins.client.render.entity.MannequinRenderer;
import me.jaackson.mannequins.common.entity.Mannequin;
import me.jaackson.mannequins.common.item.MannequinItem;
import me.jaackson.mannequins.common.network.ClientboundAttackMannequin;
import me.jaackson.mannequins.common.network.ClientboundOpenMannequinScreen;
import me.jaackson.mannequins.common.network.ServerboundSetMannequinPose;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

public class Mannequins {
    public static final String MOD_ID = "mannequins";

    public static Supplier<Item> mannequinItem;
    public static Supplier<EntityType<Mannequin>> mannequinEntity;

    public static Supplier<SoundEvent> mannequinBreakSound;
    public static Supplier<SoundEvent> mannequinFallSound;
    public static Supplier<SoundEvent> mannequinHitSound;
    public static Supplier<SoundEvent> mannequinPlaceSound;

    public static void init() {
        mannequinItem = RegistryBridge.registerItem("mannequin", new MannequinItem(new Item.Properties().stacksTo(16).tab(CreativeModeTab.TAB_DECORATIONS)));
        mannequinEntity = RegistryBridge.registerEntity("mannequin", EntityType.Builder.of(Mannequin::new, MobCategory.MISC).sized(0.5F, 1.975F).clientTrackingRange(10).build("mannequin"));

        mannequinBreakSound = RegistryBridge.registerSound("entity.mannequin.break", new SoundEvent(new ResourceLocation(Mannequins.MOD_ID, "entity.mannequin.break")));
        mannequinFallSound = RegistryBridge.registerSound("entity.mannequin.fall", new SoundEvent(new ResourceLocation(Mannequins.MOD_ID, "entity.mannequin.fall")));
        mannequinHitSound = RegistryBridge.registerSound("entity.mannequin.hit", new SoundEvent(new ResourceLocation(Mannequins.MOD_ID, "entity.mannequin.hit")));
        mannequinPlaceSound = RegistryBridge.registerSound("entity.mannequin.place", new SoundEvent(new ResourceLocation(Mannequins.MOD_ID, "entity.mannequin.place")));

        NetworkBridge.registerClientbound(ClientboundAttackMannequin.CHANNEL, ClientboundAttackMannequin.class, ClientboundAttackMannequin::write, ClientboundAttackMannequin::read, ClientboundAttackMannequin::handle);
        NetworkBridge.registerClientbound(ClientboundOpenMannequinScreen.CHANNEL, ClientboundOpenMannequinScreen.class, ClientboundOpenMannequinScreen::write, ClientboundOpenMannequinScreen::read, ClientboundOpenMannequinScreen::handle);
        NetworkBridge.registerServerbound(ServerboundSetMannequinPose.CHANNEL, ServerboundSetMannequinPose.class, ServerboundSetMannequinPose::write, ServerboundSetMannequinPose::read, ServerboundSetMannequinPose::handle);

        RegistryBridge.registerEntityAttributes(mannequinEntity, () -> Mannequin.createLivingAttributes().add(Attributes.KNOCKBACK_RESISTANCE, 1.0));
    }

    public static void clientInit() {
        RegistryBridge.registerSprite(new ResourceLocation(Mannequins.MOD_ID, "item/empty_mannequin_slot_mainhand"), InventoryMenu.BLOCK_ATLAS);
    }

    public static void commonSetup() {}

    public static void clientSetup() {
        RegistryBridge.registerEntityRenderer(mannequinEntity.get(), MannequinRenderer::new);
    }

}
