package com.chup.mobcoinsplus.extras;

import com.chup.mobcoinsplus.Main;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.text.DecimalFormat;
import java.util.Map;
import java.util.UUID;

public class SpigotExpansion extends PlaceholderExpansion {

    @Override
    public String getIdentifier() {
        return "mobcoinsplus";
    }

    @Override
    public String getAuthor() {
        return "GupAChup";
    }

    @Override
    public String getVersion() {
        return "1.6.3";
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public String onPlaceholderRequest(Player player, String params) {
        if (player == null) {
            return "";
        }
        if(params.equals("playerbalance")) {
            if(Main.points.containsKey(player.getUniqueId())) {
                return String.valueOf(Main.points.get(player.getUniqueId()));
            } else {
                return String.valueOf(0);
            }
        } else if(params.equals("playerbalance_formatted")) {
            DecimalFormat formatter = new DecimalFormat("###,###,###,###,###,###,###,###");
            if(Main.points.containsKey(player.getUniqueId())) {
                return formatter.format(Main.points.get(player.getUniqueId()));
            } else {
                return String.valueOf(0);
            }
        } else if(params.contains("coinstop_player_")) {
            Map<UUID, Integer> top = CoinsTop.sortByValue(Main.points);
            int cycle = 0;
            int position = Integer.parseInt(ChatColor.stripColor(params).replaceAll("[^0-9]", ""));
            for (Map.Entry<UUID, Integer> en : top.entrySet()) {
                String name = Bukkit.getServer().getOfflinePlayer(en.getKey()).getName();
                cycle = cycle + 1;
                if (cycle == position) {
                    return name;
                }
            }
        } else if(params.contains("coinstop_amount_")) {
            Map<UUID, Integer> top = CoinsTop.sortByValue(Main.points);
            DecimalFormat formatter = new DecimalFormat("###,###,###,###,###,###,###,###");
            int cycle = 0;
            int position = Integer.parseInt(ChatColor.stripColor(params).replaceAll("[^0-9]", ""));
            for (Map.Entry<UUID, Integer> en : top.entrySet()) {
                cycle = cycle + 1;
                if (cycle == position) {
                    return formatter.format(en.getValue());
                }
            }
        }
        return null;
    }
}