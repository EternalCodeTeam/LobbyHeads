package com.eternalcode.lobbyheads.head.particle;

import com.eternalcode.lobbyheads.configuration.implementation.HeadsConfiguration;
import com.eternalcode.lobbyheads.head.event.HeadUpdateEvent;
import java.util.UUID;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ParticleController implements Listener {

    private final Server server;
    private final HeadsConfiguration config;

    public ParticleController(Server server, HeadsConfiguration config) {
        this.server = server;
        this.config = config;
    }

    @EventHandler
    private void onHeadUpdate(HeadUpdateEvent event) {
        UUID uuid = event.getPlayerUuid();
        Player player = this.server.getPlayer(uuid);

        if (player == null) {
            return;
        }

        if (this.config.headSettings.particleEnabled) {
            player.spawnParticle(
                this.config.headSettings.particle,
                event.getLocation(),
                this.config.headSettings.count,
                0.5,
                0.5,
                0.5);
        }
    }
}
