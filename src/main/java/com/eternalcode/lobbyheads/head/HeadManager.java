package com.eternalcode.lobbyheads.head;

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
    private final HeadRepository headRepository;

    public HeadManager(EventCaller eventCaller, HeadsConfiguration config, HeadRepository headRepository) {
        this.eventCaller = eventCaller;
        this.config = config;
        this.headRepository = headRepository;
    }

    public void addHead(Player player, Position position) {
        this.heads.computeIfAbsent(position, pos -> {
            UUID uniqueId = player.getUniqueId();
            Head head = new Head(pos, player.getName(), uniqueId);
            this.headRepository.saveHead(head);
            this.eventCaller.callEvent(new HeadCreateEvent(uniqueId, pos));
            return head;
        });
    }

    public void removeHead(Position position) {
        if (!this.heads.containsKey(position)) {
            return;
        }

        Head head = this.heads.get(position);
        this.heads.remove(position);
        this.headRepository.removeHead(head);
        this.eventCaller.callEvent(new HeadRemoveEvent(head.getPlayerUUID(), position));
    }

    public void updateHead(Player player, Position position) {
        if (!this.heads.containsKey(position)) {
            return;
        }

        Head head = this.heads.get(position);
        head.replacePlayer(player.getName(), player.getUniqueId());
        this.headRepository.updateHead(head);
        this.eventCaller.callEvent(new HeadUpdateEvent(player.getUniqueId(), position));
    }

    public Head getHead(Position position) {
        return this.heads.get(position);
    }

    public void loadHeads() {
        for (Head head : this.config.heads) {
            this.heads.put(head.getPosition(), head);
        }
    }

    public void clearHeads() {
        this.heads.clear();
    }
}
