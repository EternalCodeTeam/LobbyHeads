package com.eternalcode.lobbyheads.head;

import com.eternalcode.lobbyheads.position.Position;

import java.util.Objects;
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

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        Head head = (Head) object;
        return Objects.equals(this.position, head.position)
            && Objects.equals(this.playerName, head.playerName)
            && Objects.equals(this.playerUUID, head.playerUUID)
            && Objects.equals(this.lastReplacedUUID, head.lastReplacedUUID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.position, this.playerName, this.playerUUID, this.lastReplacedUUID);
    }
}
