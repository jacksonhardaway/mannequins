package gg.moonflower.mannequins.core;

import gg.moonflower.mannequins.client.render.entity.MannequinRenderer;
import gg.moonflower.mannequins.common.entity.Mannequin;
import gg.moonflower.mannequins.common.network.MannequinsMessages;
import gg.moonflower.mannequins.common.network.play.ClientboundAttackMannequin;
import gg.moonflower.pollen.api.event.events.entity.player.PlayerInteractEvent;
import gg.moonflower.pollen.api.event.events.registry.RegisterAtlasSpriteEvent;
import gg.moonflower.pollen.api.platform.Platform;
import gg.moonflower.pollen.api.registry.ClientRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.FishingRodItem;
import net.minecraft.world.item.ItemStack;

/**
 * @author Jackson, Ocelot
 */
public class Mannequins {
    public static final String MOD_ID = "mannequins";
    public static final Platform PLATFORM = Platform.builder(Mannequins.MOD_ID)
            .commonInit(Mannequins::commonInit)
            .commonPostInit(Mannequins::commonPostInit)
            .clientInit(Mannequins::clientInit)
            .clientPostInit(Mannequins::clientPostInit)
            .build();

    public static void commonInit() {
        MannequinsMessages.init();
        // TODO: add entity attributes
//        RegistryBridge.registerEntityAttributes(MannequinsRegistry.MANNEQUIN, () -> Mannequin.createLivingAttributes().add(Attributes.KNOCKBACK_RESISTANCE, 1.0));
    }

    public static void clientInit() {
        RegisterAtlasSpriteEvent.event(InventoryMenu.BLOCK_ATLAS).register((atlas, registry) -> registry.accept(new ResourceLocation(Mannequins.MOD_ID, "item/empty_mannequin_slot_mainhand")));
    }

    public static void commonPostInit(Platform.ModSetupContext ctx) {
        PlayerInteractEvent.RIGHT_CLICK_ITEM.register((player, level, hand) -> {
            ItemStack stack = player.getItemInHand(hand);
            if (player.level.isClientSide())
                return InteractionResultHolder.pass(stack);

            if (player.fishing == null || !(stack.getItem() instanceof FishingRodItem))
                return InteractionResultHolder.pass(stack);

            Entity hooked = player.fishing.getHookedIn();
            if (hooked instanceof Mannequin) {
                MannequinsMessages.PLAY.sendToTracking(hooked, new ClientboundAttackMannequin(hooked.getId(), (float) (Math.PI - Mth.atan2(player.getX() - hooked.getX(), player.getZ() - hooked.getZ()))));
            }

            return InteractionResultHolder.pass(stack);
        });
    }

    public static void clientPostInit(Platform.ModSetupContext ctx) {
        ClientRegistries.registerEntityRenderer(MannequinsRegistry.MANNEQUIN.get(), MannequinRenderer::new);
    }

}
