package com.eternalcode.lobbyheads.head;

import com.eternalcode.lobbyheads.configuration.ConfigurationService;
import com.eternalcode.lobbyheads.configuration.implementation.HeadsConfiguration;
import com.eternalcode.lobbyheads.event.EventCaller;
import com.eternalcode.lobbyheads.head.event.HeadCreateEvent;
import com.eternalcode.lobbyheads.head.event.HeadRemoveEvent;
import com.eternalcode.lobbyheads.head.event.HeadUpdateEvent;
import com.eternalcode.lobbyheads.position.Position;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HeadManager {

    private final Map<Position, Head> heads = new HashMap<>();
    private final EventCaller eventCaller;

    private final HeadsConfiguration config;
    private final ConfigurationService configurationService;

    public HeadManager(EventCaller eventCaller, HeadsConfiguration config, ConfigurationService configurationService) {
        this.eventCaller = eventCaller;
        this.config = config;
        this.configurationService = configurationService;
    }

    public void addHead(Player player, Position position) {
        this.heads.computeIfAbsent(position, pos -> {
            UUID uniqueId = player.getUniqueId();

            Head head = new Head(pos, player.getName(), uniqueId, uniqueId);

            this.config.heads.add(head);
            this.configurationService.save(this.config);
            this.eventCaller.callEvent(new HeadCreateEvent(uniqueId, pos));

            return head;
        });
    }

    public void removeHead(Position position) {
        if (this.heads.containsKey(position)) {
            Head head = this.heads.get(position);

            this.heads.remove(position);
            this.config.heads.remove(head);
            this.configurationService.save(this.config);

            this.eventCaller.callEvent(new HeadRemoveEvent(head.getPlayerUUID(), position));
        }
    }

    public void updateHead(Player player, Position position) {
        if (this.heads.containsKey(position)) {
            Head head = this.heads.get(position);

            head.replacePlayer(player.getName(), player.getUniqueId());

            int index = this.config.heads.indexOf(head);
            if (index != -1) {
                this.config.heads.set(index, head);
                this.configurationService.save(this.config);
            }

            this.eventCaller.callEvent(new HeadUpdateEvent(player.getUniqueId(), position));
        }
    }

    public void loadHeads() {
        for (Head head : this.config.heads) {
            this.heads.put(head.getPosition(), head);
        }
    }

    public Head getHead(Position position) {
        return this.heads.get(position);
    }

    public void clearHeads() {
        this.heads.clear();
    }
}
