package com.eternalcode.lobbyheads.head.hologram;

import com.eternalcode.commons.adventure.AdventureUtil;
import com.eternalcode.commons.bukkit.position.Position;
import com.eternalcode.commons.bukkit.position.PositionAdapter;
import com.eternalcode.lobbyheads.configuration.implementation.HeadsConfiguration;
import com.eternalcode.lobbyheads.head.Head;
import com.eternalcode.lobbyheads.head.HeadManager;
import com.eternalcode.lobbyheads.head.hologram.provider.HologramProvider;
import com.eternalcode.lobbyheads.reload.Reloadable;
import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;

import java.util.UUID;

import static com.eternalcode.lobbyheads.head.hologram.HologramNameUtil.generateHologramName;

public class HologramService implements Reloadable {

    private final HeadsConfiguration config;
    private final MiniMessage miniMessage;
    private final Server server;
    private final HeadManager headManager;
    private final HologramProvider provider;

    public HologramService(
        HeadsConfiguration config,
        MiniMessage miniMessage,
        Server server,
        HeadManager headManager,
        HologramProvider provider
    ) {
        this.config = config;
        this.miniMessage = miniMessage;
        this.server = server;
        this.headManager = headManager;
        this.provider = provider;
    }

    public void createHologram(OfflinePlayer owner, Position position, String format) {
        String parsedText = PlaceholderAPI.setPlaceholders(owner, format.replace("{PLAYER}", owner.getName()));
        Component component = miniMessage.deserialize(parsedText);
        String legacyText = AdventureUtil.SECTION_SERIALIZER.serialize(component);
        Location location = getOffsetLocation(position);

        this.provider.createHologram(generateHologramName(position), location, legacyText);
    }

    public void removeHologram(Position position) {
        this.provider.removeHologram(generateHologramName(position));
    }

    public void updateHologram(Position position, UUID uuid) {
        this.removeHologram(position);
        this.createHologram(server.getOfflinePlayer(uuid), position, config.headSettings.headFormat);
    }

    public void loadHolograms() {
        String defaultFormat = config.headSettings.defaultHeadFormat;

        for (Head head : headManager.getHeads()) {
            OfflinePlayer owner = server.getOfflinePlayer(head.getPlayerUuid());
            Position position = PositionAdapter.convert(head.getLocation());

            this.createHologram(owner, position, defaultFormat);
        }
    }

    public void updateHolograms() {
        for (Head head : headManager.getHeads()) {
            Position position = PositionAdapter.convert(head.getLocation());
            updateHologram(position, head.getPlayerUuid());
        }
    }

    private Location getOffsetLocation(Position position) {
        return PositionAdapter.convert(position).clone().add(0.5, 1.0, 0.5);
    }

    @Override
    public void reload() {
        this.updateHolograms();
    }
}
