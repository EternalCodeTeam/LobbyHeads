package com.eternalcode.lobbyheads.head;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;
import org.bukkit.Location;
import org.jetbrains.annotations.NotNull;

public class Head implements Serializable {

    private final String world;
    private final double x, y, z;
    private final float yaw, pitch;

    private String playerName;
    private UUID playerUUID;

    public Head() {
        this.world = null;
        this.x = this.y = this.z = 0;
        this.yaw = this.pitch = 0;
    }

    public Head(@NotNull Location location, String playerName, UUID playerUUID) {
        this.world = location.getWorld().getName();
        this.x = location.getX();
        this.y = location.getY();
        this.z = location.getZ();
        this.yaw = location.getYaw();
        this.pitch = location.getPitch();
        this.playerName = playerName;
        this.playerUUID = playerUUID;
    }

    public @NotNull Location getLocation() {
        return new Location(
            org.bukkit.Bukkit.getWorld(this.world),
            this.x, this.y, this.z,
            this.yaw, this.pitch
        );
    }

    public @NotNull UUID getPlayerUuid() {
        return this.playerUUID;
    }

    public @NotNull String getPlayerName() {
        return this.playerName;
    }

    public void replacePlayer(@NotNull String newPlayerName, @NotNull UUID newPlayerUUID) {
        this.playerName = newPlayerName;
        this.playerUUID = newPlayerUUID;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof Head other)) {
            return false;
        }
        return this.world.equals(other.world) &&
            this.x == other.x && this.y == other.y && this.z == other.z;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.world, this.x, this.y, this.z);
    }
}
