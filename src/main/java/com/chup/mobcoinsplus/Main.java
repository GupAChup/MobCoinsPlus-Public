package com.chup.mobcoinsplus;

import com.chup.mobcoinsplus.configuration.ConfigManager;
import com.chup.mobcoinsplus.extras.Extras;
import com.chup.mobcoinsplus.extras.MetricsLite;
import com.chup.mobcoinsplus.extras.SLAPI;
import com.chup.mobcoinsplus.extras.SpigotExpansion;
import com.chup.mobcoinsplus.listeners.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class Main extends JavaPlugin {

    public static HashMap<UUID, Integer> points = new HashMap<UUID, Integer>();
    public static ArrayList<ItemStack> allItems;
    public static HashMap<ItemStack, Integer> cost;
    public ConfigManager configManager;

    @Override
    public void onEnable() {

        allItems = new ArrayList<>();
        cost = new HashMap<>();

        File file = new File(this.getDataFolder() + "/data");
        if(!file.exists()) {
            file.mkdir();
        }

        try {
            points = (HashMap<UUID, Integer>) SLAPI.load("./plugins/MobCoinsPlus/data/coins.bin");
        } catch (FileNotFoundException e) {
            System.out.println("MobCoinsPlus >> Coins file generating!");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            allItems = (ArrayList<ItemStack>) SLAPI.bukkitLoad("./plugins/MobCoinsPlus/data/items.bin");
        } catch (FileNotFoundException e) {
            System.out.println("MobCoinsPlus >> Items file generating!");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            cost = (HashMap<ItemStack, Integer>) SLAPI.bukkitLoad("./plugins/MobCoinsPlus/data/cost.bin");
        } catch (FileNotFoundException e) {
            System.out.println("MobCoinsPlus >> Cost file generating!");
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("MobCoinsPlus >> Enabled Successfully!");
        new Config(this);

        MetricsLite metrics = new MetricsLite(this, 9663);

        this.configManager = new ConfigManager(this);
        this.configManager.load("messages.yml");
        this.configManager.save("messages.yml");

        getCommand("mobcoins").setExecutor(new MobCoinsExecutor(this));
        getCommand("mobshop").setExecutor(new MobShopExecutor(this));
        Bukkit.getPluginManager().registerEvents(new CoinListener(this), this);
        Bukkit.getPluginManager().registerEvents(new ClickListener(this), this);
        Bukkit.getPluginManager().registerEvents(new CommandListener(), this);
        Bukkit.getPluginManager().registerEvents(new JoinListener(this), this);
        Bukkit.getPluginManager().registerEvents(new DeathListener(this), this);

        try {
            new SpigotExpansion().register();
        } catch (NoClassDefFoundError e) {
            System.out.println("MobCoinsPlus >> Error: No placeholder plugin detected. Please install PAPI to use placeholders.");
        }
    }

    @Override
    public void onDisable() {

        try {
            SLAPI.save(points, "./plugins/MobCoinsPlus/data/coins.bin");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            SLAPI.bukkitSave(allItems, "./plugins/MobCoinsPlus/data/items.bin");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            SLAPI.bukkitSave(cost, "./plugins/MobCoinsPlus/data/cost.bin");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public String format(String str) {
        return ChatColor.translateAlternateColorCodes('&', str);
    }

    public FileConfiguration getMessages() {
        return this.configManager.get("messages.yml");
    }
}
