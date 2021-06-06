package me.jaackson.mannequins.common.network.handler;

import me.jaackson.mannequins.client.screen.MannequinScreen;
import me.jaackson.mannequins.common.entity.Mannequin;
import me.jaackson.mannequins.common.menu.MannequinInventoryMenu;
import me.jaackson.mannequins.common.network.ClientboundAttackMannequin;
import me.jaackson.mannequins.common.network.ClientboundOpenMannequinScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.Entity;

public class MannequinsClientNetHandler {

    public static void handleOpenMannequinScreen(ClientboundOpenMannequinScreen packet) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.level == null)
            return;

        Entity entity = minecraft.level.getEntity(packet.getEntityId());
        if (!(entity instanceof Mannequin))
            return;

        LocalPlayer player = minecraft.player;
        if (player == null)
            return;

        Mannequin mannequin = (Mannequin) entity;
        MannequinInventoryMenu mannequinMenu = new MannequinInventoryMenu(packet.getContainerId(), player.inventory, new SimpleContainer(4), mannequin);
        player.containerMenu = mannequinMenu;
        minecraft.setScreen(new MannequinScreen(mannequinMenu, player.inventory, mannequin));
    }

    public static void handleAttackMannequin(ClientboundAttackMannequin packet) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.level == null)
            return;

        Entity entity = minecraft.level.getEntity(packet.getEntityId());
        if (!(entity instanceof Mannequin))
            return;

        LocalPlayer player = minecraft.player;
        if (player == null)
            return;

        ((Mannequin) entity).onAttack(packet.getAttackYaw());
    }

}
