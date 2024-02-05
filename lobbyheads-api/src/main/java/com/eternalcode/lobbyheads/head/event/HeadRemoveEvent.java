package com.eternalcode.lobbyheads.head.event;

import org.bukkit.Location;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class HeadRemoveEvent extends Event {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    private final UUID uniqueId;
    private final Location location;

    public HeadRemoveEvent(UUID uniqueId, Location location) {
        this.uniqueId = uniqueId;
        this.location = location;
    }

    public UUID getUniqueId() {
        return this.uniqueId;
    }

    public Location getLocation() {
        return this.location;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }
}
