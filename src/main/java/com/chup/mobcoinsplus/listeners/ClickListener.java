package com.chup.mobcoinsplus.listeners;

import com.chup.mobcoinsplus.Config;
import com.chup.mobcoinsplus.Main;
import com.chup.mobcoinsplus.guis.MobShopGUI;
import com.chup.mobcoinsplus.xseries.XMaterial;
import com.chup.mobcoinsplus.xseries.XSound;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ClickListener implements Listener {

    String prefix = ChatColor.translateAlternateColorCodes('&', Config.getPluginPrefix());

    private final Main plugin;
    public ClickListener(Main plugin) {
        this.plugin = plugin;
    }

    String GUIname = Config.getMobShopGUIName();

    @EventHandler
    public void onClick(InventoryClickEvent e) {

        Player player = (Player) e.getWhoClicked();
        ItemStack item = e.getCurrentItem();

        if (item != null && item.getType() != null && e.getView().getTitle().contains(GUIname)) {
            e.setCancelled(true);
            ItemMeta meta = item.getItemMeta();
            if (meta != null && meta.getLore() != null && meta.getLore().get(0) != null && item.getItemMeta().getLore().get(0).contains("Page")) {
                for (String line : item.getItemMeta().getLore()) {
                    int page = Integer.parseInt(ChatColor.stripColor(line).replaceAll("[^0-9]", ""));
                    if (e.getRawSlot() == 48 && item.getType().equals(XMaterial.ARROW.parseMaterial())) {
                        new MobShopGUI(player, page);
                    } else if (e.getRawSlot() == 50 && item.getType().equals(XMaterial.ARROW.parseMaterial())) {
                        new MobShopGUI(player, page);
                    }
                }
            }
            if (!item.getType().equals(XMaterial.AIR.parseMaterial()) && e.getRawSlot() > 9 && e.getRawSlot() < 44
                    && e.getRawSlot() != 18 && e.getRawSlot() != 27 && e.getRawSlot() != 36 && e.getRawSlot() != 17
                    && e.getRawSlot() != 26 && e.getRawSlot() != 35) {
                if (meta.hasLore()) {
                    if (Main.points.containsKey(player.getUniqueId())) {
                        if (Main.cost.containsKey(item)) {
                            if (Main.points.get(player.getUniqueId()) >= Main.cost.get(item)) {
                                ItemStack copy = new ItemStack(item);
                                ItemMeta copyMeta = copy.getItemMeta();
                                List<String> newLore = new ArrayList<String>();
                                for (int i = 0; i < copyMeta.getLore().size() - 2; i++) {
                                    newLore.add(copyMeta.getLore().get(i));
                                }
                                int price = Main.cost.get(item);
                                int balance = Main.points.get(player.getUniqueId());
                                copyMeta.setLore(newLore);
                                copy.setItemMeta(copyMeta);
                                if(player.getInventory().firstEmpty() == -1) {
                                    String message = plugin.getMessages().getString("inventory-full");
                                    player.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', message));
                                    player.playSound(player.getLocation(), XSound.ENTITY_VILLAGER_NO.parseSound(), 1.0F, 1.0F);
                                } else {
                                    Main.points.put(player.getUniqueId(), balance - price);
                                    player.getInventory().setItem(player.getInventory().firstEmpty(), copy);
                                    player.closeInventory();
                                    String message = plugin.getMessages().getString("item-purchased");
                                    player.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', message));
                                    player.playSound(player.getLocation(), XSound.ENTITY_PLAYER_LEVELUP.parseSound(), 1.0F, 1.0F);
                                }
                            } else {
                                String message = plugin.getMessages().getString("insufficient-coins");
                                player.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', message));
                                player.playSound(player.getLocation(), XSound.ENTITY_VILLAGER_NO.parseSound(), 1.0F, 1.0F);
                            }
                        } else {
                            String message = plugin.getMessages().getString("item-unknown");
                            player.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', message));
                            player.playSound(player.getLocation(), XSound.ENTITY_VILLAGER_NO.parseSound(), 1.0F, 1.0F);
                            player.closeInventory();
                        }
                    } else {
                        String message = plugin.getMessages().getString("insufficient-coins");
                        player.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', message));
                        player.playSound(player.getLocation(), XSound.ENTITY_VILLAGER_NO.parseSound(), 1.0F, 1.0F);
                    }
                }
            }
        }
    }
}
