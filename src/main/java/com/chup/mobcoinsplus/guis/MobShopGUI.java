package com.chup.mobcoinsplus.guis;

import com.chup.mobcoinsplus.Config;
import com.chup.mobcoinsplus.Main;
import com.chup.mobcoinsplus.extras.Extras;
import com.chup.mobcoinsplus.extras.PageUtil;
import com.chup.mobcoinsplus.xseries.XMaterial;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;

public class MobShopGUI {

    DecimalFormat formatter = new DecimalFormat("###,###,###,###,###,###,###,###");

    public MobShopGUI(Player player, int page) {

        String name = Config.getMobShopGUIName();
        String currency = Config.getCurrencyName();
        String currencySymbol = Config.getCurrencySymbol();

        Inventory gui = Bukkit.createInventory(null, 54, name);
        ArrayList<ItemStack> allItems = Main.allItems;

        ItemStack left;
        ItemMeta leftMeta;
        if (PageUtil.isPageValid(allItems, page - 1, 28)) {
            int nextPage = page - 1;
            left = new ItemStack(XMaterial.ARROW.parseMaterial());
            leftMeta = left.getItemMeta();
            leftMeta.setDisplayName(ChatColor.YELLOW + "" + ChatColor.ITALIC + "Click to go left");
            leftMeta.setLore(Collections.singletonList(ChatColor.GRAY + "" + ChatColor.ITALIC + "Page - " + nextPage));
        } else {
            left = new ItemStack(Extras.getColor(Config.getBorderColor()));
            leftMeta = left.getItemMeta();
        }
        left.setItemMeta(leftMeta);
        gui.setItem(48, left);

        ItemStack right;
        ItemMeta rightMeta;
        if (PageUtil.isPageValid(allItems, page + 1, 28)) {
            int nextPage = page + 1;
            right = new ItemStack(XMaterial.ARROW.parseMaterial());
            rightMeta = right.getItemMeta();
            rightMeta.setDisplayName(ChatColor.YELLOW + "" + ChatColor.ITALIC + "Click to go right");
            rightMeta.setLore(Collections.singletonList(ChatColor.GRAY + "" + ChatColor.ITALIC + "Page - " + nextPage));
        } else {
            right = new ItemStack(Extras.getColor(Config.getBorderColor()));
            rightMeta = right.getItemMeta();
        }
        right.setItemMeta(rightMeta);
        gui.setItem(50, right);

        ItemStack coins;
        ItemMeta coinsMeta;
        coins = new ItemStack(XMaterial.PRISMARINE_CRYSTALS.parseMaterial());
        coinsMeta = coins.getItemMeta();
        int number;
        if(Main.points.containsKey(player.getUniqueId())) {
            number = Main.points.get(player.getUniqueId());
        } else {
            number = 0;
        }
        if(Main.points.containsKey(player.getUniqueId())) {
            if(Config.getCurrencySymbolStatus()) {
                coinsMeta.setDisplayName(ChatColor.AQUA + currency + ": " + ChatColor.GRAY + formatter.format(number) + currencySymbol);
            } else {
                coinsMeta.setDisplayName(ChatColor.AQUA + currency + ": " + ChatColor.GRAY + formatter.format(number));
            }
        } else {
            if (Config.getCurrencySymbolStatus()) {
                coinsMeta.setDisplayName(ChatColor.AQUA + currency + ": " + ChatColor.GRAY + "0" + currencySymbol);
            } else {
                coinsMeta.setDisplayName(ChatColor.AQUA + currency + ": " + ChatColor.GRAY + "0");
            }
        }
        coins.setItemMeta(coinsMeta);

        gui.setItem(49, coins);

        ItemStack background = new ItemStack(Extras.getColor(Config.getBorderColor()));

        gui.setItem(0, background);
        gui.setItem(1, background);
        gui.setItem(2, background);
        gui.setItem(3, background);
        gui.setItem(4, background);
        gui.setItem(5, background);
        gui.setItem(6, background);
        gui.setItem(7, background);
        gui.setItem(8, background);
        gui.setItem(9, background);
        gui.setItem(18, background);
        gui.setItem(27, background);
        gui.setItem(36, background);
        gui.setItem(45, background);
        gui.setItem(46, background);
        gui.setItem(47, background);
        gui.setItem(51, background);
        gui.setItem(52, background);
        gui.setItem(53, background);
        gui.setItem(44, background);
        gui.setItem(35, background);
        gui.setItem(26, background);
        gui.setItem(17, background);

        for (ItemStack item : PageUtil.getPageItems(allItems, page, 28)) {
            gui.setItem(gui.firstEmpty(), item);
        }
        player.openInventory(gui);
    }
}