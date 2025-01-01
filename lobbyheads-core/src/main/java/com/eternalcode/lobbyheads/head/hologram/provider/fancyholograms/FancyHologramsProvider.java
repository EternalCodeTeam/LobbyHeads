package com.eternalcode.lobbyheads.head.hologram.provider.fancyholograms;

import com.eternalcode.lobbyheads.head.hologram.provider.HologramProvider;
import de.oliver.fancyholograms.api.FancyHologramsPlugin;
import de.oliver.fancyholograms.api.HologramManager;
import de.oliver.fancyholograms.api.data.HologramData;
import de.oliver.fancyholograms.api.data.TextHologramData;
import de.oliver.fancyholograms.api.hologram.Hologram;
import java.util.Optional;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class FancyHologramsProvider implements HologramProvider {

    private final Plugin plugin;

    public FancyHologramsProvider(Plugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void createHologram(String hologramName, Location location, String text) {
        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
            HologramManager hologramManager = FancyHologramsPlugin.get().getHologramManager();

            TextHologramData hologramData = new TextHologramData(hologramName, location);
            hologramData.removeLine(0); // remove useless information line
            hologramData.setPersistent(false);
            hologramData.addLine(text);

            Hologram hologram = hologramManager.create(hologramData);

            for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                hologram.updateShownStateFor(onlinePlayer);
                hologram.forceShowHologram(onlinePlayer);
            }

            hologramManager.addHologram(hologram);
        });
    }

    @Override
    public void removeHologram(String hologramName) {
        HologramManager hologramManager = FancyHologramsPlugin.get().getHologramManager();
        Optional<Hologram> hologramOptional = hologramManager.getHologram(hologramName);

        hologramOptional.ifPresent(hologramManager::removeHologram);
    }

    @Override
    public void updateHologram(String hologramName, String text) {
        HologramManager hologramManager = FancyHologramsPlugin.get().getHologramManager();
        Optional<Hologram> hologramOptional = hologramManager.getHologram(hologramName);

        hologramOptional.ifPresent(hologram -> {
            HologramData hologramData = hologram.getData();
            if (hologramData instanceof TextHologramData textData) {
                textData.removeLine(0);
                textData.addLine(text);
            }
        });
    }


}
