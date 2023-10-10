package com.eternalcode.lobbyheads.head;

import com.eternalcode.lobbyheads.position.Position;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Head {

    private final Position position;
    private final Set<UUID> replacedUUIDs;
    private String playerName;
    private UUID playerUUID;
    private UUID replacedByUUID;

    public Head(Position position, String playerName, UUID playerUUID) {
        this.position = position;
        this.playerName = playerName;
        this.playerUUID = playerUUID;
        this.replacedUUIDs = new HashSet<>();
    }

    public Position getPosition() {
        return position;
    }

    public Set<UUID> getReplacedUUIDs() {
        return replacedUUIDs;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public UUID getPlayerUUID() {
        return playerUUID;
    }

    public void setPlayerUUID(UUID playerUUID) {
        this.playerUUID = playerUUID;
    }

    public UUID getReplacedByUUID() {
        return replacedByUUID;
    }

    public void setReplacedByUUID(UUID replacedByUUID) {
        this.replacedByUUID = replacedByUUID;
    }
}
