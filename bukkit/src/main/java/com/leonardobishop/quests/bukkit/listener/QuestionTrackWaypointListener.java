package com.leonardobishop.quests.bukkit.listener;

import com.leonardobishop.quests.bukkit.api.event.PlayerStartTrackQuestEvent;
import com.leonardobishop.quests.bukkit.api.event.PlayerStopTrackQuestEvent;
import de.md5lukas.waypoints.WaypointsPlugin;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import static org.bukkit.Bukkit.getServer;

public class QuestionTrackWaypointListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        // 在玩家上线后检测有没有追踪，有的话就开启
    }
    @EventHandler
    public void onQuestTrack(PlayerStartTrackQuestEvent event) {
        getServer().dispatchCommand(getServer().getConsoleSender(), "questpointers stopAll " + event.getPlayer().getName());// 先清空执行状态
        // 再为玩家创建目标点
        if(event.getLocation() != null)
            getServer().dispatchCommand(getServer().getConsoleSender(), "questpointers add "
                    + event.getPlayer().getName()
                    + event.getLocation().getBlockX()
                    + event.getLocation().getBlockY()
                    + event.getLocation().getBlockZ()
                    + event.getLocation().getWorld().getName()
            );

    }
    @EventHandler
    public void onQuestTrackCancel(PlayerStopTrackQuestEvent event) {
        getServer().dispatchCommand(getServer().getConsoleSender(), "questpointers stopAll " + event.getPlayer().getName());// 清空执行状态
    }
}
