package com.leonardobishop.quests.bukkit.api.event;

import com.leonardobishop.quests.common.player.QPlayer;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class PlayerStartTrackQuestEvent extends PlayerQuestEvent {
    private final static HandlerList handlers = new HandlerList();
    private final QPlayer qPlayer;

    private final Location location;

    public Location getLocation() {
        return location;
    }

    public PlayerStartTrackQuestEvent(@NotNull Player who, QPlayer qPlayer, Location loc) {
        super(who, qPlayer);
        this.qPlayer = qPlayer;
        this.location = loc;
    }

    public QPlayer getQPlayer() {
        return qPlayer;
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }


}
