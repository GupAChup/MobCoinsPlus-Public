package com.chup.mobcoinsplus;

public class Config {
    private static Main main;

    public Config(Main main) {
        Config.main = main;
        main.getConfig().options().copyDefaults();
        main.saveDefaultConfig();
    }

    public static String getPluginPrefix() {return main.getConfig().getString("plugin-prefix");}
    public static String getMainCommand() {return main.getConfig().getString("main-command");}
    public static String getMobShopCommand() {return main.getConfig().getString("mob-shop-command");}
    public static String getCurrencyName() {return main.getConfig().getString("currency-name");}

    public static boolean getCurrencySymbolStatus() {return main.getConfig().getBoolean("enable-currency-symbol");}

    public static String getCurrencySymbol() {return main.getConfig().getString("currency-symbol");}
    public static String getMobShopGUIName() {return main.format(main.getConfig().getString("mobshop-gui-name"));}

    public static boolean getMessageStatus() {return main.getConfig().getBoolean("drop-messages");}
    public static boolean getSoundStatus() {return main.getConfig().getBoolean("drop-sounds");}
    public static boolean getPermissionStatus() {return main.getConfig().getBoolean("enable-permissions");}
    public static boolean getSendMoneyStatus() {return main.getConfig().getBoolean("send-money");}
    public static boolean getCoinValueCommand() {return main.getConfig().getBoolean("return-mobcoins-amount");}

    public static double getPercentLossOnDeath() {return main.getConfig().getDouble("default-percent-loss-on-death");}

    public static String getBorderColor() {return main.getConfig().getString("gui-colors.border");}
    public static String getDummyColor() {return main.getConfig().getString("gui-colors.dummy");}

    public static int getChanceMob(String entity) {
        if(main.getConfig().contains("mob-drops." + entity + ".chance")) {
            return main.getConfig().getInt("mob-drops." + entity + ".chance");
        }
        return 0;
    }
    public static int getAmountMob(String entity) {
        if(main.getConfig().contains("mob-drops." + entity + ".amount")) {
            return main.getConfig().getInt("mob-drops." + entity + ".amount");
        }
        return 0;
    }
}
