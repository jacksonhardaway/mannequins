package gg.moonflower.mannequins.common.network.play.handler;

import gg.moonflower.mannequins.common.entity.AbstractMannequin;
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
        if (!(entity instanceof AbstractMannequin))
            return;

        LocalPlayer player = minecraft.player;
        if (player == null)
            return;

        AbstractMannequin mannequin = (AbstractMannequin) entity;
        MannequinInventoryMenu mannequinMenu = new MannequinInventoryMenu(pkt.getContainerId(), player.inventory, new SimpleContainer(4), mannequin);
        player.containerMenu = mannequinMenu;
        minecraft.setScreen(mannequin.getScreen(mannequinMenu, player.inventory));
    }

    @Override
    public void handleAttackMannequin(ClientboundAttackMannequin pkt, PollinatedPacketContext ctx) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.level == null)
            return;

        Entity entity = minecraft.level.getEntity(pkt.getEntityId());
        if (!(entity instanceof AbstractMannequin))
            return;

        LocalPlayer player = minecraft.player;
        if (player == null)
            return;

        ((AbstractMannequin) entity).onAttack(pkt.getAttackYaw());
    }

}
