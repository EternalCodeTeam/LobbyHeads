package com.eternalcode.lobbyheads.head.sound;

import com.eternalcode.lobbyheads.configuration.implementation.HeadsConfiguration;
import com.eternalcode.lobbyheads.head.event.HeadUpdateEvent;
import com.eternalcode.lobbyheads.position.Position;
import com.eternalcode.lobbyheads.position.PositionAdapter;
import org.bukkit.Location;
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
        UUID uuid = event.getUuid();
        Player player = this.server.getPlayer(uuid);

        if (player == null) {
            return;
        }

        Position position = event.getPosition();
        Location convert = PositionAdapter.convert(position);

        if (this.config.headSection.soundEnabled) {
            player.playSound(convert, this.config.headSection.sound, this.config.headSection.volume, this.config.headSection.pitch);
        }
    }
}
