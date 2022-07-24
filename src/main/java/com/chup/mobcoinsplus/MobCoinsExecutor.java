package com.chup.mobcoinsplus;

import com.chup.mobcoinsplus.extras.CoinsTop;
import com.chup.mobcoinsplus.extras.Extras;
import com.chup.mobcoinsplus.extras.NBTEditor;
import com.chup.mobcoinsplus.xseries.XMaterial;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.text.DecimalFormat;
import java.util.*;

public class MobCoinsExecutor implements CommandExecutor {
    private Player player;

    String prefix = ChatColor.translateAlternateColorCodes('&', Config.getPluginPrefix());

    private final Main plugin;

    public MobCoinsExecutor(Main plugin) {
        this.plugin = plugin;
    }

    public void sendHelpMessage(Player player) {
        if (player.isOp() || player.hasPermission("mobcoinsplus.admin")) {
            Iterator helpIterator = plugin.getMessages().getStringList("help-admin").iterator();
            while (helpIterator.hasNext()) {
                String helpMessage = (String) helpIterator.next();
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', helpMessage));
            }
        } else {
            if (plugin.getConfig().contains("enable-player-help") && plugin.getConfig().getBoolean("enable-player-help")) {
                Iterator helpIterator = plugin.getMessages().getStringList("help-player").iterator();
                while (helpIterator.hasNext()) {
                    String helpMessage = (String) helpIterator.next();
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', helpMessage));
                }
            } else {
                String message = plugin.getMessages().getString("no-permission");
                player.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', message));
            }
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        String mobCoinsCommand = Config.getMainCommand();
        String currencyName = Config.getCurrencyName();
        if (sender instanceof Player) {
            player = (Player) sender;
            // checks that a first argument exists to be tested //
            if (args.length >= 1) {
                try {
                    if (args.length == 1 && (args[0].equalsIgnoreCase("reload") || args[0].equalsIgnoreCase("rl"))) {
                        plugin.configManager.reload("messages.yml");
                        plugin.configManager.save("messages.yml");
                        plugin.reloadConfig();
                        String message = ChatColor.translateAlternateColorCodes('&', plugin.getMessages().getString("reload"));
                        player.sendMessage(prefix + message);
                    } else if (args[0].equalsIgnoreCase("send")) {
                        if (Config.getSendMoneyStatus()) {
                            if (args.length == 3) {
                                Player target = Bukkit.getPlayer(args[1]);
                                if (target != null) {
                                    if (target != player) {
                                        int amount = Integer.parseInt(args[2]);
                                        if (amount > 0) {
                                            if (Extras.getCoins(player.getUniqueId()) >= amount) {
                                                Extras.giveCoins(target, amount);
                                                Extras.removeCoins(player, amount);
                                                // send message to sender //
                                                String playerMessage = plugin.getMessages().getString("send-coins");
                                                playerMessage = playerMessage.replace("{target}", target.getName());
                                                playerMessage = playerMessage.replace("{amount}", Integer.toString(amount));
                                                player.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', playerMessage));
                                                // send message to recipient //
                                                String targetMessage = plugin.getMessages().getString("given-coins");
                                                targetMessage = targetMessage.replace("{player}", player.getName());
                                                targetMessage = targetMessage.replace("{amount}", Integer.toString(amount));
                                                target.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', targetMessage));
                                            } else {
                                                String message = plugin.getMessages().getString("negative-coins");
                                                player.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', message));
                                            }
                                        } else {
                                            String message = plugin.getMessages().getString("invalid-amount");
                                            player.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', message));
                                        }
                                    } else {
                                        String message = plugin.getMessages().getString("self-send");
                                        player.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', message));
                                    }
                                } else {
                                    String message = plugin.getMessages().getString("invalid-player");
                                    player.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', message));
                                }
                            } else {
                                sendHelpMessage(player);
                            }
                        } else {
                            sendHelpMessage(player);
                        }
                    } else if (args[0].equalsIgnoreCase("amount")) {
                        if (args.length == 2 && args[0].equalsIgnoreCase("amount")) {
                            if (Config.getPermissionStatus()) {
                                if (player.isOp() || player.hasPermission("mobcoinsplus.admin") || (player.hasPermission("mobcoinsplus.amount"))) {
                                    if (args[1] != null) {
                                        Player target = Bukkit.getPlayer(args[1]);
                                        if (target != null) {
                                            int playerCoins = Extras.getCoins(target.getUniqueId());
                                            String message = plugin.getMessages().getString("player-balance");
                                            message = message.replace("{total-coins}", Integer.toString(playerCoins));
                                            message = message.replace("{currency}", currencyName);
                                            message = message.replace("{player}", target.getName());
                                            player.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', message));
                                        } else {
                                            String message = plugin.getMessages().getString("invalid-player");
                                            player.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', message));
                                        }
                                    }
                                } else {
                                    sendHelpMessage(player);
                                }
                            } else {
                                if (args[1] != null) {
                                    Player target = Bukkit.getPlayer(args[1]);
                                    if (target != null) {
                                        int playerCoins = Extras.getCoins(target.getUniqueId());
                                        String message = plugin.getMessages().getString("player-balance");
                                        message = message.replace("{total-coins}", Integer.toString(playerCoins));
                                        message = message.replace("{currency}", currencyName);
                                        message = message.replace("{player}", target.getName());
                                        player.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', message));
                                    } else {
                                        String message = plugin.getMessages().getString("invalid-player");
                                        player.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', message));
                                    }
                                }
                            }
                        } else {
                            sendHelpMessage(player);
                        }
                    } else if (args[0].equalsIgnoreCase("top")) {
                        if (args.length == 1 && args[0].equalsIgnoreCase("top")) {
                            if (Config.getPermissionStatus()) {
                                if (player.isOp() || player.hasPermission("mobcoinsplus.admin") || (player.hasPermission("mobcoinsplus.top"))) {
                                    Map<UUID, Integer> top = CoinsTop.sortByValue(Main.points);
                                    DecimalFormat formatter = new DecimalFormat("###,###,###,###,###,###,###,###");
                                    int cycle = 0;
                                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getMessages().getString("mobcoins-top-title")));
                                    for (Map.Entry<UUID, Integer> en : top.entrySet()) {
                                        String name = Bukkit.getServer().getOfflinePlayer(en.getKey()).getName();
                                        String format = ChatColor.translateAlternateColorCodes('&', plugin.getMessages().getString("mobcoins-top-format"));
                                        format = format.replace("{name}", name);
                                        format = format.replace("{amount}", formatter.format(en.getValue()));
                                        format = format.replace("{position}", Integer.toString(cycle + 1));
                                        player.sendMessage(format);
                                        cycle = cycle + 1;
                                        if (cycle == 10) {
                                            break;
                                        }
                                    }
                                } else {
                                    sendHelpMessage(player);
                                }
                            } else {
                                Map<UUID, Integer> top = CoinsTop.sortByValue(Main.points);
                                DecimalFormat formatter = new DecimalFormat("###,###,###,###,###,###,###,###");
                                int cycle = 0;
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getMessages().getString("mobcoins-top-title")));
                                for (Map.Entry<UUID, Integer> en : top.entrySet()) {
                                    String name = Bukkit.getServer().getOfflinePlayer(en.getKey()).getName();
                                    String format = ChatColor.translateAlternateColorCodes('&', plugin.getMessages().getString("mobcoins-top-format"));
                                    format = format.replace("{name}", name);
                                    format = format.replace("{amount}", formatter.format(en.getValue()));
                                    format = format.replace("{position}", Integer.toString(cycle + 1));
                                    player.sendMessage(format);
                                    cycle = cycle + 1;
                                    if (cycle == 10) {
                                        break;
                                    }
                                }
                            }
                        } else {
                            sendHelpMessage(player);
                        }
                    } else {
                        if (player.isOp() || player.hasPermission("mobcoinsplus.admin")) {
                            if (args.length == 3 && args[0].equalsIgnoreCase("give")) {
                                Player target = Bukkit.getPlayer(args[1]);
                                if (target != null) {
                                    int newAmount = Integer.parseInt(args[2]);
                                    Extras.giveCoins(target, newAmount);
                                    int playerCoins = Extras.getCoins(target.getUniqueId());
                                    String message = plugin.getMessages().getString("player-new-balance");
                                    message = message.replace("{total-coins}", Integer.toString(playerCoins));
                                    message = message.replace("{currency}", currencyName);
                                    message = message.replace("{player}", target.getName());
                                    player.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', message));
                                } else {
                                    String message = plugin.getMessages().getString("invalid-player");
                                    player.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', message));
                                }
                            } else if (args.length == 3 && args[0].equalsIgnoreCase("remove")) {
                                Player target = Bukkit.getPlayer(args[1]);
                                if (target != null) {
                                    int newAmount = Integer.parseInt(args[2]);
                                    int playerCoins = Extras.getCoins(target.getUniqueId());
                                    if ((playerCoins - newAmount) >= 0) {
                                        Extras.removeCoins(target, newAmount);
                                        String message = plugin.getMessages().getString("player-new-balance");
                                        message = message.replace("{total-coins}", Integer.toString(Extras.getCoins(target.getUniqueId())));
                                        message = message.replace("{currency}", currencyName);
                                        message = message.replace("{player}", target.getName());
                                        player.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', message));
                                    } else {
                                        String message = plugin.getMessages().getString("target-negative-coins");
                                        player.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', message));
                                    }
                                } else {
                                    String message = plugin.getMessages().getString("invalid-player");
                                    player.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', message));
                                }
                            } else if (args.length == 3 && args[0].equalsIgnoreCase("set")) {
                                Player target = Bukkit.getPlayer(args[1]);
                                if (target != null) {
                                    int newAmount = Integer.parseInt(args[2]);
                                    Extras.setCoins(target, newAmount);
                                    int playerCoins = Extras.getCoins(target.getUniqueId());
                                    String message = plugin.getMessages().getString("player-new-balance");
                                    message = message.replace("{total-coins}", Integer.toString(playerCoins));
                                    message = message.replace("{currency}", currencyName);
                                    message = message.replace("{player}", target.getName());
                                    player.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', message));
                                } else {
                                    String message = plugin.getMessages().getString("invalid-player");
                                    player.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', message));
                                }
                            } else if (args.length == 2 && args[0].equalsIgnoreCase("additem")) {
                                if (args[1].equals("dummy")) {
                                    ItemStack dummy = new ItemStack(Extras.getColor(Config.getDummyColor()));
                                    Random dummyRan = new Random();
                                    int dummyRanChoice = dummyRan.nextInt(1000000000);
                                    dummy = NBTEditor.set(dummy, dummyRanChoice, "RandomToPreventSimilarity");
                                    Main.allItems.add(dummy);
                                    Main.cost.put(dummy, 0);
                                    String message = plugin.getMessages().getString("item-added");
                                    player.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', message));
                                } else {
                                    ItemStack item = Extras.duplicateItem(player);
                                    if (item.getType() != null && item.getType() != XMaterial.AIR.parseMaterial()) {
                                        int price = Integer.parseInt(args[1]);
                                        ItemMeta itemMeta = item.getItemMeta();
                                        String currencySymbol = Config.getCurrencySymbol();
                                        String priceFormat = ChatColor.translateAlternateColorCodes('&', plugin.getMessages().getString("price-format"));
                                        priceFormat = priceFormat.replace("{amount}", Integer.toString(price));
                                        if (itemMeta.getLore() != null && itemMeta.hasLore()) {
                                            List<String> lore = new ArrayList<>();
                                            for (int i = 0; i < itemMeta.getLore().size(); i++) {
                                                lore.add(itemMeta.getLore().get(i));
                                            }
                                            lore.add("");
                                            if (Config.getCurrencySymbolStatus()) {
                                                lore.add(priceFormat + currencySymbol);
                                            } else {
                                                lore.add(priceFormat);
                                            }
                                            itemMeta.setLore(lore);
                                        } else {
                                            List<String> lore = new ArrayList<>();
                                            lore.add("");
                                            if (Config.getCurrencySymbolStatus()) {
                                                lore.add(priceFormat + currencySymbol);
                                            } else {
                                                lore.add(priceFormat);
                                            }
                                            itemMeta.setLore(lore);
                                        }
                                        item.setItemMeta(itemMeta);
                                        Main.allItems.add(item);
                                        Main.cost.put(item, price);
                                        String message = plugin.getMessages().getString("item-added");
                                        player.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', message));
                                    } else {
                                        String message = plugin.getMessages().getString("invalid-item");
                                        player.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', message));
                                    }
                                }
                            } else if (args.length == 2 && args[0].equalsIgnoreCase("removeitem")) {
                                int id = Integer.parseInt(args[1]) - 1;
                                if (id + 1 > 0 && Main.allItems.size() >= Integer.parseInt(args[1])) {
                                    if (Main.cost.containsKey(Main.allItems.get(id))) {
                                        Main.cost.remove(Main.allItems.get(id));
                                        Main.allItems.remove(id);
                                        String message = plugin.getMessages().getString("item-removed");
                                        player.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', message));
                                    } else {
                                        String message = plugin.getMessages().getString("invalid-id");
                                        player.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', message));
                                    }
                                } else {
                                    String message = plugin.getMessages().getString("invalid-id");
                                    player.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', message));
                                }
                            } else if (args.length == 2 && args[0].equalsIgnoreCase("edit")) {
                                int cost = Integer.parseInt(args[1]);
                                ItemStack item = Extras.duplicateItem(player);

                                Boolean exists = false;
                                for (ItemStack items : Main.cost.keySet()) {
                                    ItemStack copy = new ItemStack(items);
                                    ItemMeta copyMeta = copy.getItemMeta();
                                    List<String> newLore = new ArrayList<String>();
                                    for (int i = 0; i < copyMeta.getLore().size() - 2; i++) {
                                        newLore.add(copyMeta.getLore().get(i));
                                    }
                                    copyMeta.setLore(newLore);
                                    copy.setItemMeta(copyMeta);

                                    if (copy.isSimilar(item)) {
                                        Main.cost.put(items, cost);
                                        exists = true;
                                    }
                                }
                                if (!exists) {
                                    String message = plugin.getMessages().getString("new-item");
                                    player.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', message));
                                }
                            } else {
                                sendHelpMessage(player);
                            }
                        } else {
                            sendHelpMessage(player);
                        }
                    }
                } catch (NumberFormatException e) {
                    String message = plugin.getMessages().getString("invalid-number");
                    player.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', message));
                }
            } else {
                if (Config.getCoinValueCommand()) {
                    int playerCoins = Extras.getCoins(player.getUniqueId());
                    String message = plugin.getMessages().getString("self-check");
                    message = message.replace("{total-coins}", Integer.toString(playerCoins));
                    message = message.replace("{currency}", currencyName);
                    player.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', message));
                } else {
                    sendHelpMessage(player);
                }
            }
        } else {
            try {
                if (args.length == 1 && (args[0].equalsIgnoreCase("reload") || args[0].equalsIgnoreCase("rl"))) {
                    plugin.configManager.reload("messages.yml");
                    plugin.configManager.save("messages.yml");
                    plugin.reloadConfig();
                    String message = ChatColor.translateAlternateColorCodes('&', plugin.getMessages().getString("reload"));
                    Bukkit.getConsoleSender().sendMessage(prefix + message);
                } else if (args.length == 3 && args[0].equalsIgnoreCase("give")) {
                    Player target = Bukkit.getPlayer(args[1]);
                    if (target != null) {
                        int newAmount = Integer.parseInt(args[2]);
                        Extras.giveCoins(target, newAmount);
                        int playerCoins = Extras.getCoins(target.getUniqueId());
                        System.out.println("MobCoinsPlus >> " + target.getName() + " now has " + playerCoins + " " + currencyName + "!");
                    } else {
                        System.out.println("MobCoinsPlus >> Error: This player is not online.");
                    }
                } else if (args.length == 3 && args[0].equalsIgnoreCase("remove")) {
                    Player target = Bukkit.getPlayer(args[1]);
                    if (target != null) {
                        int newAmount = Integer.parseInt(args[2]);
                        int playerCoins = Extras.getCoins(target.getUniqueId());
                        if ((playerCoins - newAmount) >= 0) {
                            Extras.removeCoins(target, newAmount);
                            String message = plugin.getMessages().getString("player-new-balance");
                            message = message.replace("{total-coins}", Integer.toString(Extras.getCoins(target.getUniqueId())));
                            message = message.replace("{currency}", currencyName);
                            message = message.replace("{player}", target.getName());
                            Bukkit.getConsoleSender().sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', message));
                        } else {
                            String message = plugin.getMessages().getString("target-negative-coins");
                            Bukkit.getConsoleSender().sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', message));
                        }
                    } else {
                        System.out.println("MobCoinsPlus >> Error: This player is not online.");
                    }
                } else if (args.length == 3 && args[0].equalsIgnoreCase("set")) {
                    Player target = Bukkit.getPlayer(args[1]);
                    if (target != null) {
                        int newAmount = Integer.parseInt(args[2]);
                        Extras.setCoins(target, newAmount);
                        int playerCoins = Extras.getCoins(target.getUniqueId());
                        System.out.println("MobCoinsPlus >> " + target.getName() + " now has " + playerCoins + " " + currencyName + "!");
                    } else {
                        System.out.println("MobCoinsPlus >> Error: This player is not online.");
                    }
                } else if (args.length == 2 && args[0].equalsIgnoreCase("amount")) {
                    Player target = Bukkit.getPlayer(args[1]);
                    if (target != null) {
                        int playerCoins = Extras.getCoins(target.getUniqueId());
                        System.out.println("MobCoinsPlus >> " + target.getName() + " has " + playerCoins + " " + currencyName + "!");
                    } else {
                        System.out.println("MobCoinsPlus >> Error: This player is not online.");
                    }
                } else if (args.length == 2 && args[0].equalsIgnoreCase("additem")) {
                    System.out.println("MobCoinsPlus >> Error: This command can't be run through console!");
                } else if (args.length == 2 && args[0].equalsIgnoreCase("removeitem")) {
                    int id = Integer.parseInt(args[1]) - 1;
                    if (id + 1 > 0 && Main.allItems.size() >= Integer.parseInt(args[1])) {
                        if (Main.cost.containsKey(Main.allItems.get(id))) {
                            Main.cost.remove(Main.allItems.get(id));
                            Main.allItems.remove(id);
                            System.out.println("MobCoinsPlus >> Item successfully removed.");
                        } else {
                            System.out.println("MobCoinsPlus >> Error: This item ID does not exist in the shop!");
                        }
                    } else {
                        System.out.println("MobCoinsPlus >> Error: This item ID does not exist in the shop!");
                    }
                } else if (args.length == 2 && args[0].equalsIgnoreCase("edit")) {
                    System.out.println("MobCoinsPlus >> Error: This command can't be run through console!");
                } else if (args.length == 3 && args[0].equalsIgnoreCase("send")) {
                    System.out.println("MobCoinsPlus >> Error: This command can't be run through console!");
                } else if (args.length == 1 && args[0].equalsIgnoreCase("help")) {
                    System.out.println("MobCoinsPlus help:");
                    System.out.println("- mc give [player] [amount]");
                    System.out.println("- mc remove [player] [amount]");
                    System.out.println("- mc set [player] [amount]");
                    System.out.println("- mc amount [player]");
                    System.out.println("- mc removeitem [id]");
                } else {
                    System.out.println("That is not a valid command! Here are your options:");
                    System.out.println("- mc give [player] [amount]");
                    System.out.println("- mc remove [player] [amount]");
                    System.out.println("- mc set [player] [amount]");
                    System.out.println("- mc amount [player]");
                    System.out.println("- mc removeitem [id]");
                }
            } catch (NumberFormatException e) {
                System.out.println("MobCoinsPlus >> That's not a valid number!");
            }
        }
        return false;
    }
}