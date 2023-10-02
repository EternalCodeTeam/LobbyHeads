package com.eternalcode.lobbyheads.head;

import com.eternalcode.lobbyheads.configuration.implementation.HeadsConfiguration;
import com.eternalcode.lobbyheads.delay.Delay;
import com.eternalcode.lobbyheads.notification.NotificationAnnouncer;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Skull;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

import java.util.Optional;

public class HeadController implements Listener {

    private final HeadsConfiguration headsConfiguration;
    private final HeadService headService;
    private final NotificationAnnouncer notificationAnnouncer;
    private final HeadBlockService headBlockService;
    private final Delay delay;

    public HeadController(HeadsConfiguration headsConfiguration, HeadService headService, NotificationAnnouncer notificationAnnouncer, HeadBlockService headBlockService) {
        this.headsConfiguration = headsConfiguration;
        this.headService = headService;
        this.notificationAnnouncer = notificationAnnouncer;
        this.headBlockService = headBlockService;
        this.delay = new Delay<>(this.headsConfiguration);
    }

    @EventHandler
    void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Block clickedBlock = event.getClickedBlock();

        if (clickedBlock == null || event.getHand() != EquipmentSlot.HAND) {
            return;
        }

        if (!this.isHead(clickedBlock)) {
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

        if (this.isHead(brokenBlock)) {
            event.setCancelled(true);
        }
    }

    private boolean isHead(Block block) {
        if (block.getType() == Material.PLAYER_HEAD) {
            Optional<HeadInfo> headInfo = this.headService.find(block.getLocation());
            return headInfo.isPresent();
        }
        return false;
    }

    private void processHeadReplacement(Player player, Block clickedBlock) {
        if (!player.hasPermission("lobbyheads.replace")) {
            this.notificationAnnouncer.sendMessage(player, this.headsConfiguration.messages.youAreNotPermittedToReplaceHeads);
            return;
        }

        BlockState state = clickedBlock.getState();

        if (!(state instanceof Skull skull)) {
            return;
        }

        Location location = clickedBlock.getLocation();
        this.headService.find(location).ifPresent(headInfo -> this.headBlockService.replaceHead(player, location, skull, headInfo));

        this.delay.markDelay(player.getUniqueId(), this.headsConfiguration.delay());
    }

}
