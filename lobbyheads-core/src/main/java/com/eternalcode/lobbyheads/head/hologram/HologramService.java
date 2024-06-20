package com.eternalcode.lobbyheads.head.hologram;

import com.eternalcode.commons.adventure.AdventureUtil;
import com.eternalcode.lobbyheads.configuration.implementation.HeadsConfiguration;
import com.eternalcode.lobbyheads.head.Head;
import com.eternalcode.lobbyheads.head.HeadManagerImpl;
import com.eternalcode.lobbyheads.position.Position;
import com.eternalcode.lobbyheads.position.PositionAdapter;
import com.eternalcode.lobbyheads.reload.Reloadable;
import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.holoeasy.HoloEasy;
import org.holoeasy.config.HologramKey;
import org.holoeasy.hologram.Hologram;
import org.holoeasy.pool.IHologramPool;

import java.util.UUID;

import static org.holoeasy.builder.HologramBuilder.hologram;
import static org.holoeasy.builder.HologramBuilder.textline;

public class HologramService implements Reloadable {

    private static final String HOLOGRAM_NAME_PREFIX = "heads#%s,%s,%s,%s";
    private static final int SPAWN_DISTANCE = 50;

    private final Plugin plugin;
    private final HeadsConfiguration config;
    private final MiniMessage miniMessage;
    private final Server server;
    private final HeadManagerImpl headManagerImpl;

    private final IHologramPool hologramPool;

    public HologramService(Plugin plugin, HeadsConfiguration config, MiniMessage miniMessage,
                           Server server, HeadManagerImpl headManagerImpl) {
        this.plugin = plugin;
        this.config = config;
        this.miniMessage = miniMessage;
        this.server = server;
        this.headManagerImpl = headManagerImpl;

        this.hologramPool = HoloEasy.startPool(plugin, SPAWN_DISTANCE);
    }

    public void createHologram(OfflinePlayer player, Position position, String headName) {
        String replace = headName.replace("{PLAYER}", player.getName());
        String string = PlaceholderAPI.setPlaceholders(player, replace);
        Component deserialize = this.miniMessage.deserialize(string);
        String serialize = AdventureUtil.SECTION_SERIALIZER.serialize(deserialize);

        HologramKey key = new HologramKey(this.plugin, this.getHologramName(position), this.hologramPool);
        Hologram hologram = hologram(key, PositionAdapter.convert(this.getLocationOffset(position)), () ->
            textline(serialize));

        this.showHologramToPlayers(hologram);
    }

    public void loadHolograms() {
        String defaultHeadFormat = this.config.headSection.defaultHeadFormat;

        for (Head head : this.headManagerImpl.getHeads()) {
            OfflinePlayer offlinePlayer = this.server.getOfflinePlayer(head.getPlayerUUID());
            this.createHologram(offlinePlayer, head.getPosition(), defaultHeadFormat);
        }
    }

    public void removeHologram(Position position) {
        HologramKey key = new HologramKey(this.plugin, this.getHologramName(position), this.hologramPool);
        this.hologramPool.remove(key);
    }

    public void updateHologram(Position position, UUID uuid) {
        this.removeHologram(position);
        this.createHologram(this.server.getOfflinePlayer(uuid), position, this.config.headSection.headFormat);
    }

    public void updateHolograms() {
        for (Head head : this.headManagerImpl.getHeads()) {
            this.updateHologram(head.getPosition(), head.getPlayerUUID());
        }
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

    @Override
    public void reload() {
        this.updateHolograms();
    }
}
