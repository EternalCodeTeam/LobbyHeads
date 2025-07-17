package com.eternalcode.lobbyheads.head.event;

import java.util.UUID;
import org.bukkit.Location;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * Called when a head is removed from the world or database.
 *
 * <p>Note: This refers to the UUID of the player who owned the head,
 * not necessarily the player who removed it.</p>
 */
public class HeadRemoveEvent extends HeadEvent {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    public HeadRemoveEvent(@NotNull UUID playerUuid, @NotNull Location location) {
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
