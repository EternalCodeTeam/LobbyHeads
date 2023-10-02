package com.eternalcode.lobbyheads.head;

import com.eternalcode.lobbyheads.configuration.implementation.HeadsConfiguration;
import com.eternalcode.lobbyheads.notification.NotificationAnnouncer;
import com.eternalcode.lobbyheads.position.Position;
import com.eternalcode.lobbyheads.position.PositionAdapter;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Skull;
import org.bukkit.entity.Player;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public class HeadBlockService {

    private final HeadsConfiguration config;
    private final HeadService headService;
    private final NotificationAnnouncer notificationAnnouncer;

    public HeadBlockService(HeadsConfiguration config, HeadService headService, NotificationAnnouncer notificationAnnouncer) {
        this.config = config;
        this.headService = headService;
        this.notificationAnnouncer = notificationAnnouncer;
    }

    void removeHead(Player player) {
        Block block = player.getTargetBlock(null, 5);

        for (HeadInfo headInfo : this.config.heads) {
            if (headInfo.getPosition().equals(PositionAdapter.convert(block.getLocation()))) {
                this.headService.removeHologram(headInfo);
                this.config.heads.remove(headInfo);
                this.config.save();
                this.notificationAnnouncer.sendMessage(player, this.config.messages.headRemoved);

                return;
            }
        }
    }

    void createHead(Player player) {
        Block block = player.getTargetBlock(null, 5);

        if (block.getType() != Material.PLAYER_HEAD) {
            this.notificationAnnouncer.sendMessage(player, this.config.messages.youAreNotLookingAtHead);
            return;
        }

        if (this.headService.find(PositionAdapter.convert(block.getLocation())).isPresent()) {
            this.notificationAnnouncer.sendMessage(player, this.config.messages.headAlreadyExists);
            return;
        }

        HeadInfo headInfo = new HeadInfo(PositionAdapter.convert(block.getLocation()), player.getName(), player.getUniqueId());

        this.headService.createHologram(player, headInfo, this.config.head.headFormat);
        this.config.heads.add(headInfo);
        this.config.save();
        this.notificationAnnouncer.sendMessage(player, this.config.messages.headAdded);
    }

    void replaceHead(Player player, Position position, Skull skull, HeadInfo headInfo) {
        UUID replacedByUUID = headInfo.getReplacedByUUID();
        Set<UUID> replacedUUIDs = headInfo.getReplacedUUIDs();

        if (replacedByUUID != null && !replacedByUUID.equals(player.getUniqueId())) {
            replacedUUIDs.remove(replacedByUUID);
        }

        if (replacedUUIDs.contains(player.getUniqueId())) {
            this.notificationAnnouncer.sendMessage(player, this.config.messages.youAreAlreadyReplaceThisHead);
            return;
        }

        headInfo.setPlayerName(player.getName());
        headInfo.setPlayerUUID(player.getUniqueId());
        headInfo.setReplacedByUUID(player.getUniqueId());
        replacedUUIDs.add(player.getUniqueId());
        this.headService.updateHologram(headInfo);

        if (this.config.head.soundEnabled) {
            player.playSound(PositionAdapter.convert(position), this.config.head.sound, this.config.head.volume, this.config.head.pitch);
        }

        if (this.config.head.particleEnabled) {
            player.spawnParticle(this.config.head.particle, PositionAdapter.convert(position), this.config.head.count, 0.5, 0.5, 0.5);
        }

        skull.setOwningPlayer(player);
        skull.update();
    }

    boolean isHead(Block block) {
        if (block.getType() == Material.PLAYER_HEAD) {
            Optional<HeadInfo> headInfo = this.headService.find(PositionAdapter.convert(block.getLocation()));
            return headInfo.isPresent();
        }
        return false;
    }

}