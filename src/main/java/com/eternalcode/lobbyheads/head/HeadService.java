package com.eternalcode.lobbyheads.head;

import com.eternalcode.lobbyheads.adventure.AdventureLegacy;
import com.eternalcode.lobbyheads.configuration.implementation.HeadsConfiguration;
import com.eternalcode.lobbyheads.position.Position;
import com.eternalcode.lobbyheads.position.PositionAdapter;
import com.github.unldenis.hologram.Hologram;
import com.github.unldenis.hologram.HologramPool;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import dev.rollczi.liteskullapi.SkullAPI;
import dev.rollczi.liteskullapi.SkullData;
import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.block.BlockState;
import org.bukkit.block.Skull;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.lang.reflect.Field;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class HeadService {

    private static final String HOLOGRAM_NAME_PREFIX = "heads#";
    private static final String SKULL_TEXTURE_PROPERTY_KEY = "textures";

    private final HologramPool hologramPool;
    private final SkullAPI skullAPI;
    private final Plugin plugin;
    private final HeadsConfiguration config;
    private final MiniMessage miniMessage;
    private final Server server;

    public HeadService(SkullAPI skullAPI, Plugin plugin, HeadsConfiguration config, MiniMessage miniMessage, Server server) {
        this.skullAPI = skullAPI;
        this.plugin = plugin;
        this.hologramPool = new HologramPool(plugin, 50);
        this.config = config;
        this.miniMessage = miniMessage;
        this.server = server;
    }

    public void createHologram(OfflinePlayer player, HeadInfo headInfo, String headName) {
        String replace = headName.replace("{PLAYER}", player.getName());
        String string = PlaceholderAPI.setPlaceholders(player, replace);
        Component deserialize = this.miniMessage.deserialize(string);
        String serialize = AdventureLegacy.SECTION_SERIALIZER.serialize(deserialize);

        Hologram hologram = Hologram.builder(this.plugin, PositionAdapter.convert(this.getLocationOffset(headInfo)))
            .addLine(serialize)
            .name(this.getHologramName(headInfo))
            .loadAndBuild(this.hologramPool);

        this.hologramPool.takeCareOf(hologram);
        this.showHologramToPlayers(hologram);

        this.createHead(headInfo);
    }

    public void loadHolograms() {
        String defaultHeadFormat = this.config.head.defaultHeadFormat;

        for (HeadInfo headInfo : this.config.heads) {
            this.createHologram(this.server.getOfflinePlayer(headInfo.getPlayerUUID()), headInfo, defaultHeadFormat);
        }
    }

    public Optional<HeadInfo> find(Position position) {
        return this.config.heads.stream()
            .filter(head -> head.getPosition().equals(position))
            .findAny();
    }

    public void removeHologram(HeadInfo headInfo) {
        this.hologramPool.getHolograms().stream()
            .filter(hologram -> hologram.getName().equals(this.getHologramName(headInfo)))
            .forEach(this.hologramPool::remove);
    }

    public void updateHologram(HeadInfo headInfo) {
        this.removeHologram(headInfo);
        this.createHologram(this.server.getOfflinePlayer(headInfo.getPlayerUUID()), headInfo, this.config.head.headFormat);
    }


    private void createHead(HeadInfo headInfo) {
        SkullData skullData = this.skullAPI.awaitSkullData(headInfo.getPlayerName(), 5, TimeUnit.SECONDS);
        BukkitScheduler scheduler = this.plugin.getServer().getScheduler();

        BlockState blockState = PositionAdapter.convert(headInfo.getPosition()).getBlock().getState();
        if (blockState instanceof Skull skull) {
            this.prepareSkullUpdate(scheduler, skullData, skull);
        }
    }



    private Position getLocationOffset(HeadInfo headInfo) {
        Location location = PositionAdapter.convert(headInfo.getPosition()).clone().add(0.5, -0.3, 0.5);
        return PositionAdapter.convert(location);
    }

    private void showHologramToPlayers(Hologram hologram) {
        for (Player onlinePlayer : this.plugin.getServer().getOnlinePlayers()) {
            hologram.show(onlinePlayer);
        }
    }

    private String getHologramName(HeadInfo headInfo) {
        Position position = headInfo.getPosition();
        return HOLOGRAM_NAME_PREFIX + position.getWorld() + "," + position.getX() + "," + position.getY() + "," + position.getZ();
    }
}
