package com.eternalcode.lobbyheads.head.hologram.provider.decentholograms;

import com.eternalcode.lobbyheads.head.hologram.provider.HologramProvider;
import eu.decentsoftware.holograms.api.DHAPI;
import eu.decentsoftware.holograms.api.holograms.Hologram;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Collections;

public class DecentHologramsProvider implements HologramProvider {

    private final Plugin plugin;

    public DecentHologramsProvider(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void createHologram(String hologramName, Location location, String text) {
        Bukkit.getScheduler().runTask(plugin, () -> {
            if (DHAPI.getHologram(hologramName) != null) {
                return;
            }

            Hologram hologram = DHAPI.createHologram(hologramName, location, false, Collections.singletonList(text));

            for (Player player : Bukkit.getOnlinePlayers()) {
                hologram.setShowPlayer(player);
            }
        });
    }

    @Override
    public void removeHologram(String hologramName) {
        Bukkit.getScheduler().runTask(plugin, () -> {
            Hologram hologram = DHAPI.getHologram(hologramName);
            if (hologram != null) {
                hologram.delete();
            }
        });
    }

    @Override
    public void updateHologram(String hologramName, String text) {
        Bukkit.getScheduler().runTask(plugin, () -> {
            Hologram hologram = DHAPI.getHologram(hologramName);
            if (hologram != null) {
                DHAPI.setHologramLines(hologram, Collections.singletonList(text));
            }
        });
    }
}
