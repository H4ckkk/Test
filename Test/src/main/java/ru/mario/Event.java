package ru.mario;


import com.sk89q.worldedit.Vector;
import com.sk89q.worldguard.LocalPlayer;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;
import ru.mario.player.PlayerData;

public class Event implements Listener {

    public Event(Plugin plugin){
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Main.getPlayerDataManager().LoadPlayerData(event.getPlayer());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event){
        Main.getPlayerDataManager().unLoadPlayerData(event.getPlayer());
    }

    @EventHandler
    public void onCraft(CraftItemEvent event){
        if (event.getRecipe().getResult().getType() == Material.WORKBENCH){
            Player player = (Player) event.getWhoClicked();
            PlayerData playerData = Main.getPlayerDataManager().getPlayerData(player.getName());
            if (!playerData.isCraft()){
                Main.getEconomy().depositPlayer(player, 100);
                player.sendMessage(ChatColor.GREEN + "+100$");
                playerData.setCraft(true);
            }
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        PlayerData playerData = Main.getPlayerDataManager().getPlayerData(player.getName());

        if (playerData.isFoundExit()) return;

        LocalPlayer localPlayer = Main.getWorldGuardPlugin().wrapPlayer(player);
        Vector playerVector = localPlayer.getPosition();
        RegionManager regionManager = Main.getWorldGuardPlugin().getRegionManager(player.getWorld());
        ApplicableRegionSet applicableRegionSet = regionManager.getApplicableRegions(playerVector);
        boolean isSpawn = false;
        for (ProtectedRegion region : applicableRegionSet.getRegions()){
            if (region.getId().equalsIgnoreCase("spawn")){
                isSpawn = true;
                break;
            }
        }

        if (!isSpawn){
            Main.getEconomy().depositPlayer(player, 300);
            player.sendMessage(ChatColor.GREEN + "+300$");
            playerData.setFoundExit(true);
        }
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event){
        if (event.isCancelled()){
            return;
        }


        Block block = event.getBlock();
        if (block.getType() == Material.COAL_ORE || block.getType() == Material.IRON_ORE || block.getType() == Material.GOLD_ORE || block.getType() == Material.DIAMOND_ORE || block.getType() == Material.EMERALD_ORE || block.getType() == Material.REDSTONE_ORE || block.getType() == Material.GLOWING_REDSTONE_ORE || block.getType() == Material.LAPIS_ORE|| block.getType() == Material.QUARTZ_ORE){
            Player player = event.getPlayer();
            PlayerData playerData = Main.getPlayerDataManager().getPlayerData(player.getName());
            playerData.addOre();
            if (playerData.getOres() >= 100 && !playerData.isMined()){
                Main.getEconomy().depositPlayer(player, 300);
                player.sendMessage(ChatColor.GREEN + "+300$");
                playerData.setMined(true);
            }
        }
    }


}
