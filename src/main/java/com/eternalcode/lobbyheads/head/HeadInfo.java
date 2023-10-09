package com.eternalcode.lobbyheads.head;

import com.eternalcode.lobbyheads.position.Position;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class HeadInfo {

    private final Position position;
    private final Set<UUID> replacedUUIDs;
    private String playerName;
    private UUID playerUUID;
    private UUID replacedByUUID;

    public HeadInfo(Position position, String playerName, UUID playerUUID) {
        this.position = position;
        this.playerName = playerName;
        this.playerUUID = playerUUID;
        this.replacedUUIDs = new HashSet<>();
    }

    public Position getPosition() {
        return this.position;
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
        return Collections.unmodifiableSet(this.replacedUUIDs);
    }

    public UUID getReplacedByUUID() {
        return this.replacedByUUID;
    }

    public void setReplacedByUUID(UUID replacedByUUID) {
        this.replacedByUUID = replacedByUUID;
    }
}