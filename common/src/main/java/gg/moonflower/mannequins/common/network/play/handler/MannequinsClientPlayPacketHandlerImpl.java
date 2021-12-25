package gg.moonflower.mannequins.common.network.play.handler;

import gg.moonflower.mannequins.client.screen.MannequinScreen;
import gg.moonflower.mannequins.common.entity.Mannequin;
import gg.moonflower.mannequins.common.menu.MannequinInventoryMenu;
import gg.moonflower.mannequins.common.network.play.ClientboundAttackMannequin;
import gg.moonflower.mannequins.common.network.play.ClientboundOpenMannequinScreen;
import gg.moonflower.pollen.api.network.packet.PollinatedPacketContext;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.Entity;

/**
 * @author Ocelot
 */
public class MannequinsClientPlayPacketHandlerImpl implements MannequinsClientPlayPacketHandler {

    @Override
    public void handleOpenMannequinScreen(ClientboundOpenMannequinScreen pkt, PollinatedPacketContext ctx) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.level == null)
            return;

        Entity entity = minecraft.level.getEntity(pkt.getEntityId());
        if (!(entity instanceof Mannequin))
            return;

        LocalPlayer player = minecraft.player;
        if (player == null)
            return;

        Mannequin mannequin = (Mannequin) entity;
        MannequinInventoryMenu mannequinMenu = new MannequinInventoryMenu(pkt.getContainerId(), player.getInventory(), new SimpleContainer(4), mannequin);
        player.containerMenu = mannequinMenu;
        minecraft.execute(() -> minecraft.setScreen(new MannequinScreen(mannequinMenu, player.getInventory(), mannequin)));
    }

    @Override
    public void handleAttackMannequin(ClientboundAttackMannequin pkt, PollinatedPacketContext ctx) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.level == null)
            return;

        Entity entity = minecraft.level.getEntity(pkt.getEntityId());
        if (!(entity instanceof Mannequin))
            return;

        LocalPlayer player = minecraft.player;
        if (player == null)
            return;

        ((Mannequin) entity).onAttack(pkt.getAttackYaw());
    }

}
