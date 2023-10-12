package com.eternalcode.lobbyheads.head.hologram;

import com.eternalcode.lobbyheads.adventure.AdventureLegacy;
import com.eternalcode.lobbyheads.configuration.implementation.HeadsConfiguration;
import com.eternalcode.lobbyheads.head.Head;
import com.eternalcode.lobbyheads.position.Position;
import com.eternalcode.lobbyheads.position.PositionAdapter;
import com.github.unldenis.hologram.Hologram;
import com.github.unldenis.hologram.HologramPool;
import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Optional;
import java.util.UUID;

public class HologramService {

    private static final String HOLOGRAM_NAME_PREFIX = "heads#%s,%s,%s,%s";

    private final HologramPool hologramPool;
    private final Plugin plugin;
    private final HeadsConfiguration config;
    private final MiniMessage miniMessage;
    private final Server server;

    public HologramService(Plugin plugin, HeadsConfiguration config, MiniMessage miniMessage, Server server) {
        this.plugin = plugin;
        this.hologramPool = new HologramPool(plugin, 50);
        this.config = config;
        this.miniMessage = miniMessage;
        this.server = server;
    }

    public void createHologram(OfflinePlayer player, Position position, String headName) {
        String replace = headName.replace("{PLAYER}", player.getName());
        String string = PlaceholderAPI.setPlaceholders(player, replace);
        Component deserialize = this.miniMessage.deserialize(string);
        String serialize = AdventureLegacy.SECTION_SERIALIZER.serialize(deserialize);

        Hologram hologram = Hologram.builder(this.plugin, PositionAdapter.convert(this.getLocationOffset(position)))
            .addLine(serialize)
            .name(this.getHologramName(position))
            .loadAndBuild(this.hologramPool);

        this.hologramPool.takeCareOf(hologram);
        this.showHologramToPlayers(hologram);
    }

    public void loadHolograms() {
        String defaultHeadFormat = this.config.headSection.defaultHeadFormat;

        for (Head head : this.config.heads) {
            this.createHologram(this.server.getOfflinePlayer(head.getPlayerUUID()), head.getPosition(), defaultHeadFormat);
        }
    }

    public Optional<Head> find(Position position) {
        return this.config.heads.stream()
            .filter(head -> head.getPosition().equals(position))
            .findAny();
    }

    public void removeHologram(Position position) {
        this.hologramPool.getHolograms().stream()
            .filter(hologram -> hologram.getName().equals(this.getHologramName(position)))
            .forEach(this.hologramPool::remove);
    }

    public void updateHologram(Position position, UUID uuid) {
        this.removeHologram(position);
        this.createHologram(this.server.getOfflinePlayer(uuid), position, this.config.headSection.headFormat);
    }

    private Position getLocationOffset(Position position) {
        Location location = PositionAdapter.convert(position).clone().add(0.5, -0.3, 0.5);
        return PositionAdapter.convert(location);
    }

    private void showHologramToPlayers(Hologram hologram) {
        for (Player onlinePlayer : this.plugin.getServer().getOnlinePlayers()) {
            hologram.show(onlinePlayer);
        }
    }

    private String getHologramName(Position position) {
        return String.format(HOLOGRAM_NAME_PREFIX, position.world(), position.x(), position.y(), position.z());
    }
}
