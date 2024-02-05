package com.eternalcode.lobbyheads.head.event;

import java.util.UUID;
import org.bukkit.Location;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class HeadRemoveEvent extends Event {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    private final UUID uniqueId;
    private final Location location;

    public HeadRemoveEvent(UUID uniqueId, Location location) {
        this.uniqueId = uniqueId;
        this.location = location;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
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
}
