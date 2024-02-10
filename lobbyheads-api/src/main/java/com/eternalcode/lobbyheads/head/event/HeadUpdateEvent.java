package com.eternalcode.lobbyheads.head.event;

import java.util.UUID;
import org.bukkit.Location;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * This event is called when a head is updated.
 **/
public class HeadUpdateEvent extends Event {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    private final UUID playerUniqueId;
    private final Location location;

    public HeadUpdateEvent(UUID playerUniqueId, Location location) {
        this.playerUniqueId = playerUniqueId;
        this.location = location;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

    public UUID getPlayerUniqueId() {
        return this.playerUniqueId;
    }

    public Location getLocation() {
        return this.location;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLER_LIST;
    }
}
