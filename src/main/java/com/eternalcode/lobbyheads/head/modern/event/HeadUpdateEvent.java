package com.eternalcode.lobbyheads.head.modern.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class HeadUpdateEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private final UUID player;

    public HeadUpdateEvent(UUID player) {
        this.player = player;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public UUID getPlayer() {
        return this.player;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return handlers;
    }
}
