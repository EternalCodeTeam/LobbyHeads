package com.eternalcode.lobbyheads.head.hologram;

import com.eternalcode.lobbyheads.configuration.implementation.HeadsConfiguration;
import com.eternalcode.lobbyheads.head.event.HeadCreateEvent;
import com.eternalcode.lobbyheads.head.event.HeadRemoveEvent;
import com.eternalcode.lobbyheads.head.event.HeadUpdateEvent;
import com.eternalcode.lobbyheads.position.Position;
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
    void createHologram(HeadCreateEvent event) {
        UUID player = event.getPlayer();
        OfflinePlayer offlinePlayer = this.server.getOfflinePlayer(player);

        this.hologramService.createHologram(offlinePlayer, event.getPosition(), this.config.headSection.defaultHeadFormat);
    }

    @EventHandler
    void removeHologram(HeadRemoveEvent event) {
        this.hologramService.removeHologram(event.getPosition());
    }

    @EventHandler
    void updateHologram(HeadUpdateEvent event) {
        UUID player = event.getPlayer();
        Position position = event.getPosition();

        this.hologramService.updateHologram(position, player);
    }
}
