package com.eternalcode.lobbyheads.head.event;

import java.util.UUID;
import org.bukkit.Location;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * Called when a head is created in the world.
 */
public class HeadCreateEvent extends HeadEvent {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    public HeadCreateEvent(@NotNull UUID playerUuid, @NotNull Location location) {
        super(playerUuid, location);
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static @NotNull HandlerList getHandlerList() {
        return HANDLER_LIST;
    }
}
