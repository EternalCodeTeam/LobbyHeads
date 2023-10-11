package com.eternalcode.lobbyheads.head.block;

import com.eternalcode.lobbyheads.configuration.implementation.HeadsConfiguration;
import com.eternalcode.lobbyheads.head.Head;
import com.eternalcode.lobbyheads.head.HeadManager;
import com.eternalcode.lobbyheads.notification.NotificationAnnouncer;
import com.eternalcode.lobbyheads.position.Position;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class BlockService {

    private final HeadsConfiguration config;
    private final NotificationAnnouncer notificationAnnouncer;
    private final HeadManager headManager;

    public BlockService(HeadsConfiguration config, NotificationAnnouncer notificationAnnouncer, HeadManager headManager) {
        this.config = config;
        this.notificationAnnouncer = notificationAnnouncer;
        this.headManager = headManager;
    }

    public void createHeadBlock(Location location, Player player, Position convert) {
        if (location.getBlock().getType() != Material.PLAYER_HEAD) {
            this.notificationAnnouncer.sendMessage(player, this.config.messages.youAreNotLookingAtHead);
            return;
        }

        if (this.headManager.getHead(convert) != null) {
            this.notificationAnnouncer.sendMessage(player, this.config.messages.headAlreadyExists);
            return;
        }

        this.headManager.addHead(player, convert);
        this.notificationAnnouncer.sendMessage(player, this.config.messages.headAdded);
    }

    public void removeHeadBlock(Position convert, Player player) {
        Head head = this.headManager.getHead(convert);

        if (head == null) {
            this.notificationAnnouncer.sendMessage(player, this.config.messages.youAreNotLookingAtHead);
            return;
        }

        this.headManager.removeHead(convert);
        this.notificationAnnouncer.sendMessage(player, this.config.messages.headRemoved);
    }
}
