package com.eternalcode.lobbyheads.head.event;

import com.eternalcode.lobbyheads.position.Position;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class HeadCreateEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private final UUID player;
    private final Position position;

    public HeadCreateEvent(UUID player, Position position) {
        this.player = player;
        this.position = position;
    }

    public UUID getPlayer() {
        return this.player;
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
