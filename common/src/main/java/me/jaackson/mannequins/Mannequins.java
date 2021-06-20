package me.jaackson.mannequins;

import me.jaackson.mannequins.bridge.EventBridge;
import me.jaackson.mannequins.bridge.NetworkBridge;
import me.jaackson.mannequins.bridge.RegistryBridge;
import me.jaackson.mannequins.client.render.entity.MannequinRenderer;
import me.jaackson.mannequins.common.entity.Mannequin;
import me.jaackson.mannequins.common.network.ClientboundAttackMannequin;
import me.jaackson.mannequins.common.network.ClientboundOpenMannequinScreen;
import me.jaackson.mannequins.common.network.ServerboundSetMannequinPose;
import me.jaackson.mannequins.common.network.handler.MannequinsClientPlayHandler;
import me.jaackson.mannequins.common.network.handler.MannequinsServerPlayHandler;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.FishingRodItem;
import net.minecraft.world.item.ItemStack;

/**
 * @author Jackson, Ocelot
 */
public class Mannequins {
    public static final String MOD_ID = "mannequins";

    public static void commonInit() {
        MannequinsRegistry.register();
        RegistryBridge.registerEntityAttributes(MannequinsRegistry.MANNEQUIN, () -> Mannequin.createLivingAttributes().add(Attributes.KNOCKBACK_RESISTANCE, 1.0));
    }

    public static void clientInit() {
        RegistryBridge.registerSprite(new ResourceLocation(Mannequins.MOD_ID, "item/empty_mannequin_slot_mainhand"), InventoryMenu.BLOCK_ATLAS);
    }

    public static void commonPostInit() {
        EventBridge.registerRightClickItemEvent((player, level, hand) -> {
            if (player.level.isClientSide())
                return;

            ItemStack stack = player.getItemInHand(hand);

            if (player.fishing == null || !(stack.getItem() instanceof FishingRodItem))
                return;

            Entity hooked = player.fishing.getHookedIn();
            if (hooked instanceof Mannequin) {
                NetworkBridge.sendToTracking(hooked, new ClientboundAttackMannequin(hooked.getId(), (float) (Math.PI - Mth.atan2(player.getX() - hooked.getX(), player.getZ() - hooked.getZ()))));
            }
        });
    }

    public static void clientPostInit() {
        RegistryBridge.registerEntityRenderer(MannequinsRegistry.MANNEQUIN.get(), MannequinRenderer::new);
    }

    public static void commonNetworkingInit() {
        NetworkBridge.registerPlayToServer(ServerboundSetMannequinPose.CHANNEL, ServerboundSetMannequinPose.class, ServerboundSetMannequinPose::new, MannequinsServerPlayHandler::handleSetMannequinPose);
    }

    public static void clientNetworkingInit() {
        NetworkBridge.registerPlayToClient(ClientboundAttackMannequin.CHANNEL, ClientboundAttackMannequin.class, ClientboundAttackMannequin::new, () -> MannequinsClientPlayHandler::handleAttackMannequin);
        NetworkBridge.registerPlayToClient(ClientboundOpenMannequinScreen.CHANNEL, ClientboundOpenMannequinScreen.class, ClientboundOpenMannequinScreen::new, () -> MannequinsClientPlayHandler::handleOpenMannequinScreen);
    }

}
