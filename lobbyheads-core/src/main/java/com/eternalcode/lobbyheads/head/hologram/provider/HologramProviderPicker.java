package com.eternalcode.lobbyheads.head.hologram.provider;

import com.eternalcode.lobbyheads.head.hologram.provider.fancyholograms.FancyHologramsProvider;
import org.bukkit.plugin.Plugin;

public class HologramProviderPicker {



    public HologramProvider pickProvider(Plugin plugin) {
        if (plugin.getServer().getPluginManager().isPluginEnabled("FancyHolograms")) {
            return new FancyHologramsProvider(plugin);
        }

        throw new HologramProviderNotFoundException("No hologram provider found");
    }
}
