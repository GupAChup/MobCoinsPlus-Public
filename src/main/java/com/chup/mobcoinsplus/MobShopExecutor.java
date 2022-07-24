package com.chup.mobcoinsplus;

import com.chup.mobcoinsplus.guis.MobShopGUI;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MobShopExecutor implements CommandExecutor {

    String prefix = ChatColor.translateAlternateColorCodes('&', Config.getPluginPrefix());

    private final Main plugin;
    public MobShopExecutor(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if(sender instanceof Player) {
            Player player = (Player) sender;
            if(Config.getPermissionStatus()) {
                if (player.hasPermission("mobcoinsplus.mobshop")) {
                    new MobShopGUI(player, 1);
                } else {
                    String message = plugin.getMessages().getString("no-permission");
                    player.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', message));
                }
            } else {
                new MobShopGUI(player, 1);
            }
        } else {
            System.out.println("This command can't be run through console.");
        }
        return false;
    }
}