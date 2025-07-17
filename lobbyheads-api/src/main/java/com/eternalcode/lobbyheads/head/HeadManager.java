package com.eternalcode.lobbyheads.head;

import java.util.Collection;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Manages all heads in the lobby.
 */
public interface HeadManager {

    /**
     * Adds a head for the given player at the specified location.
     */
    void addHead(@NotNull Player player, @NotNull Location location);

    /**
     * Removes a head from the specified location.
     */
    void removeHead(@NotNull Location location);

    /**
     * Updates an existing head's data (e.g. skin) for the given player.
     */
    void updateHead(@NotNull Player player, @NotNull Location location);

    /**
     * Returns the head at the specified location, or null if none exists.
     */
    @Nullable Head getHead(@NotNull Location location);

    /**
     * Returns all known heads.
     */
    @NotNull Collection<Head> getHeads();
}
