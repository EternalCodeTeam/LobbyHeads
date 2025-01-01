package com.eternalcode.lobbyheads.head.hologram;

import static com.eternalcode.lobbyheads.head.hologram.HologramNameUtil.generateHologramName;

import com.eternalcode.commons.adventure.AdventureUtil;
import com.eternalcode.lobbyheads.configuration.implementation.HeadsConfiguration;
import com.eternalcode.lobbyheads.head.Head;
import com.eternalcode.lobbyheads.head.HeadManager;
import com.eternalcode.lobbyheads.head.hologram.provider.HologramProvider;
import com.eternalcode.lobbyheads.position.Position;
import com.eternalcode.lobbyheads.position.PositionAdapter;
import com.eternalcode.lobbyheads.reload.Reloadable;
import java.util.UUID;
import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;

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

    public void createHologram(OfflinePlayer player, Position position, String headName) {
        String placeholderText = PlaceholderAPI.setPlaceholders(player, headName.replace("{PLAYER}", player.getName()));
        Component deserialized = this.miniMessage.deserialize(placeholderText);
        String serialize = AdventureUtil.SECTION_SERIALIZER.serialize(deserialized);
        Location location = PositionAdapter.convert(this.getLocationOffset(position));

        this.provider.createHologram(generateHologramName(position), location, serialize);
    }

    public void loadHolograms() {
        String defaultHeadFormat = this.config.headSection.defaultHeadFormat;

        for (Head head : this.headManager.getHeads()) {
            OfflinePlayer offlinePlayer = this.server.getOfflinePlayer(head.getPlayerUUID());
            this.createHologram(offlinePlayer, head.getPosition(), defaultHeadFormat);
        }
    }

    public void removeHologram(Position position) {
        this.provider.removeHologram(generateHologramName(position));
    }

    public void updateHologram(Position position, UUID uuid) {
        this.removeHologram(position);
        this.createHologram(this.server.getOfflinePlayer(uuid), position, this.config.headSection.headFormat);
    }

    public void updateHolograms() {
        for (Head head : this.headManager.getHeads()) {
            this.updateHologram(head.getPosition(), head.getPlayerUUID());
        }
    }

    private Position getLocationOffset(Position position) {
        Location location = PositionAdapter.convert(position).clone().add(0.5, 1, 0.5);
        return PositionAdapter.convert(location);
    }

    @Override
    public void reload() {
        this.updateHolograms();
    }
}
