package com.eternalcode.lobbyheads.head.hologram.provider;

import org.bukkit.Location;

public interface HologramProvider {

    void createHologram(String hologramName, Location location, String text);

    void removeHologram(String hologramName);

    void updateHologram(String hologramName, String text);

}
