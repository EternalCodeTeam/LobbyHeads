package com.eternalcode.lobbyheads.head.sound;

import com.eternalcode.lobbyheads.configuration.implementation.HeadsConfiguration;
import com.eternalcode.lobbyheads.head.event.HeadUpdateEvent;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.UUID;

public class SoundController implements Listener {

    private final Server server;
    private final HeadsConfiguration config;

    public SoundController(Server server, HeadsConfiguration config) {
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

        if (this.config.headSettings.soundEnabled) {
            this.config.headSettings.sound.play(player, this.config.headSettings.volume, this.config.headSettings.pitch);
        }
    }
}
