package com.eternalcode.lobbyheads.head.event;

import java.util.UUID;
import org.bukkit.Location;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class HeadUpdateEvent extends Event {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    private final UUID uuid;
    private final Location location;

    public HeadUpdateEvent(UUID uuid, Location location) {
        this.uuid = uuid;
        this.location = location;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

    public UUID getUuid() {
        return this.uuid;
    }

    public Location getLocation() {
        return this.location;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLER_LIST;
    }
}
