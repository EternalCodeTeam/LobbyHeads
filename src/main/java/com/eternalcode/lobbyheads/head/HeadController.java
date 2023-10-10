package com.eternalcode.lobbyheads.head;

import com.eternalcode.lobbyheads.configuration.implementation.HeadsConfiguration;
import com.eternalcode.lobbyheads.delay.Delay;
import com.eternalcode.lobbyheads.notification.NotificationAnnouncer;
import com.eternalcode.lobbyheads.position.PositionAdapter;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import java.util.UUID;

public class HeadController implements Listener {

    private final HeadsConfiguration config;
    private final Delay<UUID> delay;
    private final HeadManager headManager;
    private final NotificationAnnouncer notificationAnnouncer;

    public HeadController(HeadsConfiguration config, HeadManager headManager, NotificationAnnouncer notificationAnnouncer) {
        this.config = config;
        this.headManager = headManager;
        this.notificationAnnouncer = notificationAnnouncer;
        this.delay = new Delay<>(this.config);
    }

    @EventHandler
    private void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Block clickedBlock = event.getClickedBlock();
        Location location = clickedBlock.getLocation();

        if (event.getHand() != EquipmentSlot.HAND) {
            return;
        }

        Head head = this.headManager.getHead(PositionAdapter.convert(location));

        if (head == null) {
            return;
        }

        UUID playerUUID = player.getUniqueId();

        if (this.delay.hasDelay(playerUUID)) {
            return;
        }

        this.headManager.updateHead(player, PositionAdapter.convert(location));

        this.delay.markDelay(playerUUID);
    }

    @EventHandler
    private void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block clickedBlock = event.getBlock();
        Location location = clickedBlock.getLocation();

        Head head = this.headManager.getHead(PositionAdapter.convert(location));

        if (head == null) {
            return;
        }

        this.notificationAnnouncer.sendMessage(player, this.config.messages.headRemoved);
        event.setCancelled(true);
    }
}
