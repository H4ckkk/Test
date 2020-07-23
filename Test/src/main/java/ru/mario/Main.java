package ru.mario;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import ru.mario.config.Configs;
import ru.mario.player.PlayerData;
import ru.mario.player.PlayerDataManager;

public final class Main extends JavaPlugin {

    private static Economy economy;
    private static Main plugin;
    private static Configs configs;
    private static PlayerDataManager playerDataManager;
    private static WorldGuardPlugin worldGuardPlugin;

    @Override
    public void onEnable() {
        plugin = this;
        configs = new Configs();
        playerDataManager = new PlayerDataManager();
        worldGuardPlugin = getWorldGuard();
        if (!setupEconomy() ) {
            Bukkit.getConsoleSender().sendMessage("Vault is not loaded");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        new Event(this);
    }

    @Override
    public void onDisable() {
        for (Player player : Bukkit.getOnlinePlayers()){
            playerDataManager.unLoadPlayerData(player);
        }
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        economy = rsp.getProvider();
        return economy != null;
    }

    public WorldGuardPlugin getWorldGuard() {
        Plugin plugin = this.getServer().getPluginManager().getPlugin("WorldGuard");

        if (plugin == null || !(plugin instanceof WorldGuardPlugin)) {
            return null;
        }

        return (WorldGuardPlugin) plugin;
    }

    public static Economy getEconomy() {
        return economy;
    }

    public static Main getInstance() {
        return plugin;
    }

    public static Configs getConfigs() {
        return configs;
    }

    public static PlayerDataManager getPlayerDataManager() {
        return playerDataManager;
    }

    public static WorldGuardPlugin getWorldGuardPlugin() {
        return worldGuardPlugin;
    }
}
