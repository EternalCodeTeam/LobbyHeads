package com.eternalcode.lobbyheads.head.block;

import com.eternalcode.lobbyheads.configuration.implementation.HeadsConfiguration;
import com.eternalcode.lobbyheads.head.Head;
import com.eternalcode.lobbyheads.head.HeadManager;
import com.eternalcode.lobbyheads.notification.NotificationAnnouncer;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class BlockService {

    private final HeadsConfiguration config;
    private final NotificationAnnouncer notificationAnnouncer;
    private final HeadManager headManager;

    public BlockService(
        HeadsConfiguration config,
        NotificationAnnouncer notificationAnnouncer,
        HeadManager headManager
    ) {
        this.config = config;
        this.notificationAnnouncer = notificationAnnouncer;
        this.headManager = headManager;
    }

    public void createHeadBlock(Location location, Player player) {
        if (location.getBlock().getType() != Material.PLAYER_HEAD) {
            this.notificationAnnouncer.sendMessage(player, this.config.messages.notLookingAtHead);
            return;
        }

        if (this.headManager.getHead(location) != null) {
            this.notificationAnnouncer.sendMessage(player, this.config.messages.headExists);
            return;
        }

        this.headManager.addHead(player, location);
        this.notificationAnnouncer.sendMessage(player, this.config.messages.headAdded);
    }

    public void removeHeadBlock(Location location, Player player) {
        Head head = this.headManager.getHead(location);

        if (head == null) {
            this.notificationAnnouncer.sendMessage(player, this.config.messages.notLookingAtHead);
            return;
        }

        this.headManager.removeHead(location);
        this.notificationAnnouncer.sendMessage(player, this.config.messages.headRemoved);
    }
}
