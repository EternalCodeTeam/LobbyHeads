package com.eternalcode.lobbyheads.head;

import com.eternalcode.lobbyheads.position.Position;

import java.util.UUID;

public class Head {

    private final Position position;
    private String playerName;
    private UUID playerUUID;
    private UUID lastReplacedUUID;

    public Head(Position position, String playerName, UUID playerUUID, UUID lastReplacedUUID) {
        this.position = position;
        this.playerName = playerName;
        this.playerUUID = playerUUID;
        this.lastReplacedUUID = lastReplacedUUID;
    }

    public Position getPosition() {
        return this.position;
    }

    public String getPlayerName() {
        return this.playerName;
    }

    public UUID getPlayerUUID() {
        return this.playerUUID;
    }

    public UUID getLastReplacedUUID() {
        return this.lastReplacedUUID;
    }

    public void replacePlayer(String newPlayerName, UUID newPlayerUUID) {
        this.lastReplacedUUID = this.playerUUID;
        this.playerName = newPlayerName;
        this.playerUUID = newPlayerUUID;
    }
}
