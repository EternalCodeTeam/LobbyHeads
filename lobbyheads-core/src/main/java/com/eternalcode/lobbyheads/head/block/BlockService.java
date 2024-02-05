package com.eternalcode.lobbyheads.head.block;

import com.eternalcode.lobbyheads.configuration.implementation.HeadsConfiguration;
import com.eternalcode.lobbyheads.head.Head;
import com.eternalcode.lobbyheads.head.HeadManagerImpl;
import com.eternalcode.lobbyheads.notification.NotificationAnnouncer;
import com.eternalcode.lobbyheads.position.Position;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class BlockService {

    private final HeadsConfiguration config;
    private final NotificationAnnouncer notificationAnnouncer;
    private final HeadManagerImpl headManagerImpl;

    public BlockService(HeadsConfiguration config, NotificationAnnouncer notificationAnnouncer, HeadManagerImpl headManagerImpl) {
        this.config = config;
        this.notificationAnnouncer = notificationAnnouncer;
        this.headManagerImpl = headManagerImpl;
    }

    public void createHeadBlock(Location location, Player player, Position convert) {
        if (location.getBlock().getType() != Material.PLAYER_HEAD) {
            this.notificationAnnouncer.sendMessage(player, this.config.messages.playerNotLookingAtHead);
            return;
        }

        if (this.headManagerImpl.getHead(convert) != null) {
            this.notificationAnnouncer.sendMessage(player, this.config.messages.headAlreadyExists);
            return;
        }

        this.headManagerImpl.addHead(player, convert);
        this.notificationAnnouncer.sendMessage(player, this.config.messages.headAdded);
    }

    public void removeHeadBlock(Position convert, Player player) {
        Head head = this.headManagerImpl.getHead(convert);

        if (head == null) {
            this.notificationAnnouncer.sendMessage(player, this.config.messages.playerNotLookingAtHead);
            return;
        }

        this.headManagerImpl.removeHead(convert);
        this.notificationAnnouncer.sendMessage(player, this.config.messages.headRemoved);
    }
}
