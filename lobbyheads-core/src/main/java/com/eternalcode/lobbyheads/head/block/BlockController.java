package com.eternalcode.lobbyheads.head.block;

import com.eternalcode.lobbyheads.head.event.HeadCreateEvent;
import com.eternalcode.lobbyheads.head.event.HeadUpdateEvent;
import com.eternalcode.lobbyheads.position.Position;
import com.eternalcode.lobbyheads.position.PositionAdapter;
import dev.rollczi.liteskullapi.SkullAPI;
import dev.rollczi.liteskullapi.SkullData;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.Skull;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;

public class BlockController implements Listener {

    private final Plugin plugin;
    private final BukkitScheduler scheduler;
    private final SkullAPI skullAPI;

    public BlockController(Plugin plugin, BukkitScheduler scheduler, SkullAPI skullAPI) {
        this.plugin = plugin;
        this.scheduler = scheduler;
        this.skullAPI = skullAPI;
    }

    @EventHandler
    void headCreate(HeadCreateEvent event) {
        Position position = PositionAdapter.convert(event.getLocation());
        UUID uuid = event.getPlayerUniqueId();

        this.processSkullUpdate(position, uuid);
    }

    @EventHandler
    private void headUpdate(HeadUpdateEvent event) {
        Position position = PositionAdapter.convert(event.getLocation());
        UUID uuid = event.getPlayerUniqueId();

        this.processSkullUpdate(position, uuid);
    }

    private void processSkullUpdate(Position position, UUID uuid) {
        Location convert = PositionAdapter.convert(position);
        Block block = convert.getBlock();

        if (!(block.getState() instanceof Skull skull)) {
            return;
        }

        SkullData skullData = this.skullAPI.awaitSkullData(uuid, 5, TimeUnit.SECONDS);
        this.prepareSkullUpdate(this.scheduler, uuid, skull);
    }

    private void prepareSkullUpdate(BukkitScheduler scheduler, UUID uuid, Skull skull) {
        scheduler.runTask(this.plugin, () -> this.updateSkull(uuid, skull));
    }

    private void updateSkull(UUID uuid, Skull skull) {
        skull.setOwningPlayer(Bukkit.getOfflinePlayer(uuid));
        skull.update();
    }
}
