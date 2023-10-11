package com.eternalcode.lobbyheads.head.event;

import com.eternalcode.lobbyheads.position.Position;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class HeadRemoveEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private final UUID uuid;
    private final Position position;

    public HeadRemoveEvent(UUID uuid, Position position) {
        this.uuid = uuid;
        this.position = position;
    }

    public UUID getUuid() {
        return this.uuid;
    }

    public Position getPosition() {
        return this.position;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
