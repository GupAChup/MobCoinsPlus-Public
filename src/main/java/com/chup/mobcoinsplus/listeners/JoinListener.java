package com.chup.mobcoinsplus.listeners;

import com.chup.mobcoinsplus.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    private static Main main;
    public JoinListener(Main main) { JoinListener.main = main; }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        if(!Main.points.containsKey(player.getUniqueId())) {
            int amount = 0;
            if(main.getConfig().contains("starting-amount")) {
                amount = main.getConfig().getInt("starting-amount");
            }
            Main.points.put(player.getUniqueId(), amount);
        }
    }
}
