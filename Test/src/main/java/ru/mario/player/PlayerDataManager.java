package ru.mario.player;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import ru.mario.Main;
import ru.mario.config.CustomConfig;

import java.util.HashMap;
import java.util.Map;

public class PlayerDataManager {

    private HashMap<String, PlayerData> playerDataHashMap;

    public PlayerDataManager (){
        this.playerDataHashMap = new HashMap<>();
    }

    public void LoadPlayerData(Player player){

        String name = player.getName();

        if (CustomConfig.exist("players/"+name)){
            CustomConfig cfg = Main.getConfigs().get("players/"+name);

            boolean isCraft = cfg.get().getBoolean("isCraft");
            boolean isFoundExit = cfg.get().getBoolean("isFoundExit");
            boolean isMined = cfg.get().getBoolean("isMined");
            int ores = cfg.get().getInt("ores");

            playerDataHashMap.put(name, new PlayerData(isCraft,isFoundExit,isMined, ores));
        } else {
            playerDataHashMap.put(name, new PlayerData(false,false,false, 0));
            save(player);
        }

        player.sendMessage(ChatColor.GREEN + "Loaded Data");
    }

    public void unLoadPlayerData(Player player){
        save(player);
        playerDataHashMap.remove(player.getName());
    }

    public PlayerData getPlayerData(String name){
        return playerDataHashMap.get(name);
    }

    public void save(Player player){
        String name = player.getName();
        CustomConfig cfg = Main.getConfigs().get("players/"+name);
        PlayerData playerData = getPlayerData(name);

        cfg.get().set("isCraft",playerData.isCraft());
        cfg.get().set("isFoundExit",playerData.isFoundExit());
        cfg.get().set("isMined",playerData.isMined());
        cfg.get().set("ores",playerData.getOres());

        cfg.save();
    }
}
