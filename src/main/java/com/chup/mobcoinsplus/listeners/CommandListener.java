package com.chup.mobcoinsplus.listeners;

import com.chup.mobcoinsplus.Config;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CommandListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onCommand(PlayerCommandPreprocessEvent event) {
        String message = event.getMessage();
        String[] sMsg = message.split(" ");
        String mainCmd = Config.getMainCommand();
        String mobShopCmd = Config.getMobShopCommand();
        if (sMsg[0].equalsIgnoreCase("/" + mainCmd)) {
            String messageJ = "/mobcoins";
            for (int i = 1; i < sMsg.length; i++) {
                messageJ = messageJ + " " + sMsg[i];
            }
            event.setMessage(messageJ);
        } else if (sMsg[0].equalsIgnoreCase("/" + mobShopCmd)) {
            String messageJ = "/mobshop";
            for (int i = 1; i < sMsg.length; i++) {
                messageJ = messageJ + " " + sMsg[i];
            }
            event.setMessage(messageJ);
        }
    }
}
