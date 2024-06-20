package com.eternalcode.lobbyheads.head;

import com.eternalcode.lobbyheads.position.Position;
import com.eternalcode.lobbyheads.position.PositionAdapter;
import java.util.Objects;
import java.util.UUID;
import org.bukkit.Location;

public class Head {

    private final Position position;
    private String playerName;
    private UUID playerUUID;

    public Head(Position position, String playerName, UUID playerUUID) {
        this.position = position;
        this.playerName = playerName;
        this.playerUUID = playerUUID;
    }

    public Position getPosition() {
        return this.position;
    }

    public Location getLocation() {
        return PositionAdapter.convert(this.position);
    }

    public String getPlayerName() {
        return this.playerName;
    }

    public UUID getPlayerUUID() {
        return this.playerUUID;
    }

    public void replacePlayer(String newPlayerName, UUID newPlayerUUID) {
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
        return Objects.equals(this.position, head.position);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.position);
    }
}
