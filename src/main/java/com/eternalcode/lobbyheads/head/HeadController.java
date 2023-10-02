package com.eternalcode.lobbyheads.head;

import com.eternalcode.lobbyheads.configuration.implementation.HeadsConfiguration;
import com.eternalcode.lobbyheads.delay.Delay;
import com.eternalcode.lobbyheads.notification.NotificationAnnouncer;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Skull;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

public class HeadController implements Listener {

    private static final String REPLACEMENT_PERMISSION = "lobbyheads.replace";

    private final HeadsConfiguration config;
    private final HeadService headService;
    private final NotificationAnnouncer notificationAnnouncer;
    private final HeadBlockService headBlockService;
    private final Delay delay;

    public HeadController(HeadsConfiguration config, HeadService headService, NotificationAnnouncer notificationAnnouncer, HeadBlockService headBlockService) {
        this.config = config;
        this.headService = headService;
        this.notificationAnnouncer = notificationAnnouncer;
        this.headBlockService = headBlockService;
        this.delay = new Delay<>(this.config);
    }

    @EventHandler
    void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Block clickedBlock = event.getClickedBlock();

        if (clickedBlock == null || event.getHand() != EquipmentSlot.HAND) {
            return;
        }

        if (!this.headBlockService.isHead(clickedBlock)) {
            return;
        }

        if (this.delay.hasDelay(player.getUniqueId())) {
            return;
        }

        this.processHeadReplacement(player, clickedBlock);
    }

    @EventHandler
    void onBlockBreak(BlockBreakEvent event) {
        Block brokenBlock = event.getBlock();

        if (this.headBlockService.isHead(brokenBlock)) {
            event.setCancelled(true);
        }
    }

    void processHeadReplacement(Player player, Block clickedBlock) {
        if (!player.hasPermission(REPLACEMENT_PERMISSION)) {
            this.notificationAnnouncer.sendMessage(player, this.config.messages.youAreNotPermittedToReplaceHeads);
            return;
        }

        BlockState state = clickedBlock.getState();

        if (!(state instanceof Skull skull)) {
            return;
        }

        Location location = clickedBlock.getLocation();
        this.headService.find(location).ifPresent(headInfo -> this.headBlockService.replaceHead(player, location, skull, headInfo));

        this.delay.markDelay(player.getUniqueId(), this.config.delay());
    }

}
