package com.chup.mobcoinsplus.extras;

import com.chup.mobcoinsplus.Config;
import com.chup.mobcoinsplus.Main;
import com.chup.mobcoinsplus.xseries.XMaterial;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Iterator;
import java.util.UUID;

public class Extras {

    static String prefix = ChatColor.translateAlternateColorCodes('&', Config.getPluginPrefix());

    public static void giveCoins(Player player, int amount) {
        if (!Main.points.containsKey(player.getUniqueId())) {

            Main.points.put(player.getUniqueId(), 0 + amount);

        } else {

            int playerPoints = Main.points.get(player.getUniqueId());

            Main.points.put(player.getUniqueId(), Main.points.get(player.getUniqueId()) + amount);

        }

    }

    public static void removeCoins(Player player, int amount) {

        if (!Main.points.containsKey(player.getUniqueId())) {

            Main.points.put(player.getUniqueId(), 0 - amount);

        } else {

            int playerPoints = Main.points.get(player.getUniqueId());

            Main.points.put(player.getUniqueId(), playerPoints - amount);

        }

    }

    public static void setCoins(Player player, int amount) {

        Main.points.put(player.getUniqueId(), amount);

    }

    public static Integer getCoins(UUID uuid) {
        if (!Main.points.containsKey(uuid)) {
            return 0;
        }
        return Main.points.get(uuid);
    }

    public static ItemStack duplicateItem(Player player) {
        if (player.getItemInHand() != null) {
            ItemStack duplicateItem = new ItemStack(player.getItemInHand());
            ItemMeta duplicateItemMeta = duplicateItem.getItemMeta();
            duplicateItem.setItemMeta(duplicateItemMeta);
            return duplicateItem;

        }
        return null;
    }

    public static ItemStack getColor(String color) {
        if(color.equalsIgnoreCase("black")) {
            return XMaterial.BLACK_STAINED_GLASS_PANE.parseItem();
        } else if (color.equalsIgnoreCase("red")) {
            return XMaterial.RED_STAINED_GLASS_PANE.parseItem();
        } else if (color.equalsIgnoreCase("green")) {
            return XMaterial.GREEN_STAINED_GLASS_PANE.parseItem();
        } else if (color.equalsIgnoreCase("brown")) {
            return XMaterial.BROWN_STAINED_GLASS_PANE.parseItem();
        } else if (color.equalsIgnoreCase("blue")) {
            return XMaterial.BLUE_STAINED_GLASS_PANE.parseItem();
        } else if (color.equalsIgnoreCase("purple")) {
            return XMaterial.PURPLE_STAINED_GLASS_PANE.parseItem();
        } else if (color.equalsIgnoreCase("cyan")) {
            return XMaterial.CYAN_STAINED_GLASS_PANE.parseItem();
        } else if (color.equalsIgnoreCase("light gray") || color.equalsIgnoreCase("light_gray") || color.equalsIgnoreCase("lightgray")) {
            return XMaterial.LIGHT_GRAY_STAINED_GLASS_PANE.parseItem();
        } else if (color.equalsIgnoreCase("gray")) {
            return XMaterial.GRAY_STAINED_GLASS_PANE.parseItem();
        } else if (color.equalsIgnoreCase("pink")) {
            return XMaterial.PINK_STAINED_GLASS_PANE.parseItem();
        } else if (color.equalsIgnoreCase("lime")) {
            return XMaterial.LIME_STAINED_GLASS_PANE.parseItem();
        } else if (color.equalsIgnoreCase("yellow")) {
            return XMaterial.YELLOW_STAINED_GLASS_PANE.parseItem();
        } else if (color.equalsIgnoreCase("light blue") || color.equalsIgnoreCase("light_blue") || color.equalsIgnoreCase("lightblue")) {
            return XMaterial.LIGHT_BLUE_STAINED_GLASS_PANE.parseItem();
        } else if (color.equalsIgnoreCase("magenta")) {
            return XMaterial.MAGENTA_STAINED_GLASS_PANE.parseItem();
        } else if (color.equalsIgnoreCase("orange")) {
            return XMaterial.ORANGE_STAINED_GLASS_PANE.parseItem();
        } else if (color.equalsIgnoreCase("white")) {
            return XMaterial.WHITE_STAINED_GLASS_PANE.parseItem();
        }
        return XMaterial.GLASS_PANE.parseItem();
    }

}
