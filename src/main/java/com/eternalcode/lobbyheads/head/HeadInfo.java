package com.eternalcode.lobbyheads.head;

import org.bukkit.Location;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class HeadInfo {

    private final Location location;
    private final Set<UUID> replacedUUIDs;
    private String playerName;
    private UUID playerUUID;
    private UUID replacedByUUID;

    public HeadInfo(Location location, String playerName, UUID playerUUID) {
        this.location = location;
        this.playerName = playerName;
        this.playerUUID = playerUUID;
        this.replacedUUIDs = new HashSet<>();
    }

    public Location getLocation() {
        return this.location;
    }

    public String getPlayerName() {
        return this.playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public UUID getPlayerUUID() {
        return this.playerUUID;
    }

    public void setPlayerUUID(UUID playerUUID) {
        this.playerUUID = playerUUID;
    }

    public Set<UUID> getReplacedUUIDs() {
        return this.replacedUUIDs;
    }

    public UUID getReplacedByUUID() {
        return this.replacedByUUID;
    }

    public void setReplacedByUUID(UUID replacedByUUID) {
        this.replacedByUUID = replacedByUUID;
    }
}
