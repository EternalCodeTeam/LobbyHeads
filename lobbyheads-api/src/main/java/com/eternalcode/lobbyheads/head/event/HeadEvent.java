package com.eternalcode.lobbyheads.head.event;

import java.util.UUID;
import org.bukkit.Location;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * Base class for all head-related events.
 */
public abstract class HeadEvent extends Event {

    private final UUID playerUuid;
    private final Location location;

    protected HeadEvent(@NotNull UUID playerUuid, @NotNull Location location) {
        this.playerUuid = playerUuid;
        this.location = location;
    }

    /**
     * @return The UUID of the player associated with this head.
     */
    public @NotNull UUID getPlayerUuid() {
        return playerUuid;
    }

    /**
     * @return The location of the head.
     */
    public @NotNull Location getLocation() {
        return location;
    }
}
