package com.eternalcode.lobbyheads.head;

import com.eternalcode.lobbyheads.event.EventCaller;
import com.eternalcode.lobbyheads.head.event.HeadCreateEvent;
import com.eternalcode.lobbyheads.head.event.HeadRemoveEvent;
import com.eternalcode.lobbyheads.head.event.HeadUpdateEvent;
import com.eternalcode.lobbyheads.position.Position;
import com.eternalcode.lobbyheads.position.PositionAdapter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.ApiStatus.Internal;

public class HeadManagerImpl implements HeadManager {

    private final Map<Position, Head> heads = new HashMap<>();
    private final EventCaller eventCaller;

    private final HeadRepository headRepository;

    public HeadManagerImpl(EventCaller eventCaller, HeadRepository headRepository) {
        this.eventCaller = eventCaller;
        this.headRepository = headRepository;
    }

    @Internal
    public void addHead(Player player, Position position) {
        this.heads.computeIfAbsent(position, pos -> {
            UUID uniqueId = player.getUniqueId();
            Head head = new Head(pos, player.getName(), uniqueId);
            this.headRepository.saveHead(head);
            this.eventCaller.callEvent(new HeadCreateEvent(uniqueId, PositionAdapter.convert(pos)));
            return head;
        });
    }

    @Override
    public void addHead(Player player, Location location) {
        this.addHead(player, PositionAdapter.convert(location));
    }

    @Internal
    public void removeHead(Position position) {
        if (!this.heads.containsKey(position)) {
            return;
        }

        Head head = this.heads.get(position);
        this.heads.remove(position);
        this.headRepository.removeHead(head);
        this.eventCaller.callEvent(new HeadRemoveEvent(head.getPlayerUUID(), PositionAdapter.convert(position)));
    }

    @Override
    public void removeHead(Location location) {
        this.removeHead(PositionAdapter.convert(location));
    }

    @Internal
    public void updateHead(Player player, Position position) {
        if (!this.heads.containsKey(position)) {
            return;
        }

        Head head = this.heads.get(position);
        head.replacePlayer(player.getName(), player.getUniqueId());
        this.headRepository.updateHead(head);
        this.eventCaller.callEvent(new HeadUpdateEvent(player.getUniqueId(), PositionAdapter.convert(position)));
    }

    @Override
    public void updateHead(Player player, Location location) {
        this.updateHead(player, PositionAdapter.convert(location));
    }

    @Internal
    public Head getHead(Position position) {
        return this.heads.get(position);
    }

    @Override
    public Head getHead(Location location) {
        return this.getHead(PositionAdapter.convert(location));
    }

    @Override
    public Collection<Head> getHeads() {
        return this.heads.values();
    }

    public void loadHeads() {
        this.headRepository.loadHeads().thenAccept(loadedHeads -> {
            this.heads.clear();
            loadedHeads.forEach(head -> this.heads.put(head.getPosition(), head));
        });
    }

    public void clearHeads() {
        this.heads.clear();
    }
}
