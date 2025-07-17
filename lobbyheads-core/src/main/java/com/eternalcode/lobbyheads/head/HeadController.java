package com.eternalcode.lobbyheads.head;

import com.eternalcode.lobbyheads.configuration.implementation.HeadsConfiguration;
import com.eternalcode.lobbyheads.delay.Delay;
import com.eternalcode.lobbyheads.notification.NotificationAnnouncer;
import java.time.Duration;
import java.util.UUID;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

public class HeadController implements Listener {

    public static final String HEAD_REPLACE_PERMISSION = "lobbyheads.replace";

    private final HeadsConfiguration config;
    private final Delay<UUID> delay;
    private final HeadManager headManager;
    private final NotificationAnnouncer notificationAnnouncer;

    public HeadController(
        HeadsConfiguration config,
        HeadManager headManager,
        NotificationAnnouncer notificationAnnouncer
    ) {
        this.config = config;
        this.headManager = headManager;
        this.notificationAnnouncer = notificationAnnouncer;
        this.delay = new Delay<>(this.config);
    }

    @EventHandler
    private void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Block clickedBlock = event.getClickedBlock();

        if (clickedBlock == null) {
            return;
        }

        if (event.getHand() != EquipmentSlot.HAND) {
            return;
        }

        Location location = clickedBlock.getLocation();

        Head head = this.headManager.getHead(location);

        if (head == null) {
            return;
        }

        UUID playerUUID = player.getUniqueId();

        if (!player.hasPermission(HEAD_REPLACE_PERMISSION)) {
            this.notificationAnnouncer.sendMessage(player, this.config.messages.noPermissionReplace);
            return;
        }

        if (this.delay.hasDelay(playerUUID)) {
            Duration durationToExpire = this.delay.getDurationToExpire(playerUUID);

            this.notificationAnnouncer.sendMessage(player, this.config.messages.replaceCooldown
                    .replace("{duration}", String.valueOf(durationToExpire.toSeconds())));
            return;
        }

        if (head.getPlayerUuid().equals(playerUUID)) {
            this.notificationAnnouncer.sendMessage(player, this.config.messages.alreadyReplaced);
            return;
        }

        this.headManager.updateHead(player, location);
        this.delay.markDelay(playerUUID);
    }

    @EventHandler
    private void onBlockBreak(BlockBreakEvent event) {
        Block clickedBlock = event.getBlock();
        Location location = clickedBlock.getLocation();

        Head head = this.headManager.getHead(location);

        if (head == null) {
            return;
        }

        event.setCancelled(true);
    }
}
