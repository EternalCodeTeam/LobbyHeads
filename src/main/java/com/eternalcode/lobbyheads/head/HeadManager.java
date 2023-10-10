package com.eternalcode.lobbyheads.head;

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

    public HeadManager(EventCaller eventCaller) {
        this.eventCaller = eventCaller;
    }

    public void addHead(Player player, Position position) {
        if (!this.heads.containsKey(position)) {
            Head head = new Head(position, player.getName(), player.getUniqueId());

            this.heads.put(position, head);

            this.eventCaller.callEvent(new HeadCreateEvent(player.getUniqueId(), position));
        }
    }

    public void removeHead(Position position) {
        if (this.heads.containsKey(position)) {
            Head head = this.heads.get(position);

            this.heads.remove(position);

            this.eventCaller.callEvent(new HeadRemoveEvent(head.getPlayerUUID(), position));
        }
    }

    public void updateHead(Player player, Position position) {
        if (this.heads.containsKey(position)) {
            Head head = this.heads.get(position);

            head.setPlayerName(player.getName());
            head.setPlayerUUID(player.getUniqueId());
            head.setReplacedByUUID(player.getUniqueId());

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
