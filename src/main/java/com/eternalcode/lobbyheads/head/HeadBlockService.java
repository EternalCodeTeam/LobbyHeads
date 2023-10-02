package com.eternalcode.lobbyheads.head;

import com.eternalcode.lobbyheads.configuration.implementation.HeadsConfiguration;
import com.eternalcode.lobbyheads.notification.NotificationAnnouncer;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Skull;
import org.bukkit.entity.Player;

import java.util.Set;
import java.util.UUID;

public class HeadBlockService {

    private final HeadsConfiguration headsConfiguration;
    private final HeadService headService;
    private final NotificationAnnouncer notificationAnnouncer;

    public HeadBlockService(HeadsConfiguration headsConfiguration, HeadService headService, NotificationAnnouncer notificationAnnouncer) {
        this.headsConfiguration = headsConfiguration;
        this.headService = headService;
        this.notificationAnnouncer = notificationAnnouncer;
    }

    void removeHead(Player player) {
        Block block = player.getTargetBlock(null, 5);

        for (HeadInfo headInfo : this.headsConfiguration.heads) {
            if (headInfo.getLocation().equals(block.getLocation())) {
                this.headService.removeHologram(headInfo);
                this.headsConfiguration.heads.remove(headInfo);
                this.headsConfiguration.save();
                this.notificationAnnouncer.sendMessage(player, this.headsConfiguration.messages.headRemoved);

                return;
            }
        }
    }

    void createHead(Player player) {
        Block block = player.getTargetBlock(null, 5);

        if (block.getType() != Material.PLAYER_HEAD) {
            this.notificationAnnouncer.sendMessage(player, this.headsConfiguration.messages.youAreNotLookingAtHead);
            return;
        }

        if (this.headService.find(block.getLocation()).isPresent()) {
            this.notificationAnnouncer.sendMessage(player, this.headsConfiguration.messages.headAlreadyExists);
            return;
        }

        HeadInfo headInfo = new HeadInfo(block.getLocation(), player.getName(), player.getUniqueId());

        this.headService.createHologram(player, headInfo, this.headsConfiguration.head.headFormat);
        this.headsConfiguration.heads.add(headInfo);
        this.headsConfiguration.save();
        this.notificationAnnouncer.sendMessage(player, this.headsConfiguration.messages.headAdded);
    }

    void replaceHead(Player player, Location location, Skull skull, HeadInfo headInfo) {
        UUID replacedByUUID = headInfo.getReplacedByUUID();
        Set<UUID> replacedUUIDs = headInfo.getReplacedUUIDs();

        if (replacedByUUID != null && !replacedByUUID.equals(player.getUniqueId())) {
            replacedUUIDs.remove(replacedByUUID);
        }

        if (replacedUUIDs.contains(player.getUniqueId())) {
            this.notificationAnnouncer.sendMessage(player, this.headsConfiguration.messages.youAreAlreadyReplaceThisHead);
            return;
        }

        headInfo.setPlayerName(player.getName());
        headInfo.setPlayerUUID(player.getUniqueId());
        headInfo.setReplacedByUUID(player.getUniqueId());
        replacedUUIDs.add(player.getUniqueId());
        this.headService.updateHologram(headInfo);

        if (this.headsConfiguration.head.soundEnabled) {
            player.playSound(location, this.headsConfiguration.head.sound, this.headsConfiguration.head.volume, this.headsConfiguration.head.pitch);
        }

        if (this.headsConfiguration.head.particleEnabled) {
            player.spawnParticle(this.headsConfiguration.head.particle, location, this.headsConfiguration.head.count, 0.5, 0.5, 0.5);
        }

        skull.setOwningPlayer(player);
        skull.update();
    }

}
