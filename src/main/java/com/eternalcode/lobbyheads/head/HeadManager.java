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

public class HeadManager {

    private final Map<Position, Head> heads = new HashMap<>();
    private final EventCaller eventCaller;

    private final HeadsConfiguration config;

    public HeadManager(EventCaller eventCaller, HeadsConfiguration config) {
        this.eventCaller = eventCaller;
        this.config = config;
    }

    public void addHead(Player player, Position position) {
        this.heads.computeIfAbsent(position, pos -> {
            Head head = new Head(pos, player.getName(), player.getUniqueId());

            this.config.heads.add(head);
            this.eventCaller.callEvent(new HeadCreateEvent(player.getUniqueId(), pos));

            return head;
        });
    }

    public void removeHead(Position position) {
        if (this.heads.containsKey(position)) {
            Head head = this.heads.get(position);

            this.heads.remove(position);
            this.config.heads.remove(head);

            this.eventCaller.callEvent(new HeadRemoveEvent(head.getPlayerUUID(), position));
        }
    }

    public void updateHead(Player player, Position position) {
        if (this.heads.containsKey(position)) {
            Head head = this.heads.get(position);

            head.setPlayerName(player.getName());
            head.setPlayerUUID(player.getUniqueId());
            head.setLastReplacedUUID(player.getUniqueId());

            int index = this.config.heads.indexOf(head);
            if (index != -1) {
                this.config.heads.set(index, head);
            }

            this.eventCaller.callEvent(new HeadUpdateEvent(player.getUniqueId(), position));
        }
    }

    public Head getHead(Position position) {
        return this.heads.get(position);
    }

    public void clearHeads() {
        this.heads.clear();
    }
}
