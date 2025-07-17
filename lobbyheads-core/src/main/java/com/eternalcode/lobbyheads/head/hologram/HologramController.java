package com.eternalcode.lobbyheads.head.hologram;

import com.eternalcode.commons.bukkit.position.PositionAdapter;
import com.eternalcode.lobbyheads.configuration.implementation.HeadsConfiguration;
import com.eternalcode.lobbyheads.head.event.HeadCreateEvent;
import com.eternalcode.lobbyheads.head.event.HeadRemoveEvent;
import com.eternalcode.lobbyheads.head.event.HeadUpdateEvent;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.UUID;

public class HologramController implements Listener {

    private final HologramService hologramService;
    private final HeadsConfiguration config;
    private final Server server;

    public HologramController(HologramService hologramService, HeadsConfiguration config, Server server) {
        this.hologramService = hologramService;
        this.config = config;
        this.server = server;
    }

    @EventHandler
    void onHeadCreate(HeadCreateEvent event) {
        UUID playerUuid = event.getPlayerUuid();
        OfflinePlayer player = server.getOfflinePlayer(playerUuid);

        hologramService.createHologram(player, PositionAdapter.convert(event.getLocation()), config.headSettings.defaultHeadFormat);
    }

    @EventHandler
    void onHeadRemove(HeadRemoveEvent event) {
        hologramService.removeHologram(PositionAdapter.convert(event.getLocation()));
    }

    @EventHandler
    void onHeadUpdate(HeadUpdateEvent event) {
        UUID playerUuid = event.getPlayerUuid();
        Location location = event.getLocation();

        hologramService.updateHologram(PositionAdapter.convert(location), playerUuid);
    }
}
