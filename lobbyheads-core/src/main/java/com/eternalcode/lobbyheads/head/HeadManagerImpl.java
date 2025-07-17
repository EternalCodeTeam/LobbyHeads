package com.eternalcode.lobbyheads.head;

import com.eternalcode.commons.bukkit.position.Position;
import com.eternalcode.commons.bukkit.position.PositionAdapter;
import com.eternalcode.lobbyheads.event.EventCaller;
import com.eternalcode.lobbyheads.head.event.HeadCreateEvent;
import com.eternalcode.lobbyheads.head.event.HeadRemoveEvent;
import com.eternalcode.lobbyheads.head.event.HeadUpdateEvent;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class HeadManagerImpl implements HeadManager {

    private final Map<Position, Head> heads = new HashMap<>();
    private final EventCaller eventCaller;
    private final HeadRepository headRepository;

    public HeadManagerImpl(@NotNull EventCaller eventCaller, @NotNull HeadRepository headRepository) {
        this.eventCaller = eventCaller;
        this.headRepository = headRepository;
    }

    @Override
    public void addHead(@NotNull Player player, @NotNull Location location) {
        Position position = PositionAdapter.convert(location);
        heads.computeIfAbsent(
            position, pos -> {
                UUID uuid = player.getUniqueId();
                String name = player.getName();
                Head head = new Head(location, name, uuid);

                headRepository.saveHead(head);
                eventCaller.callEvent(new HeadCreateEvent(uuid, PositionAdapter.convert(pos)));
                return head;
            });
    }

    @Override
    public void removeHead(@NotNull Location location) {
        Position position = PositionAdapter.convert(location);
        Head removed = heads.remove(position);
        if (removed == null) {
            return;
        }

        headRepository.removeHead(removed);
        eventCaller.callEvent(new HeadRemoveEvent(removed.getPlayerUuid(), PositionAdapter.convert(position)));
    }

    @Override
    public void updateHead(@NotNull Player player, @NotNull Location location) {
        Position position = PositionAdapter.convert(location);
        Head head = heads.get(position);
        if (head == null) {
            return;
        }

        head.replacePlayer(player.getName(), player.getUniqueId());
        headRepository.updateHead(head);
        eventCaller.callEvent(new HeadUpdateEvent(player.getUniqueId(), PositionAdapter.convert(position)));
    }

    @Override
    public Head getHead(@NotNull Location location) {
        Position position = PositionAdapter.convert(location);
        return this.heads.get(position);
    }


    @Override
    public @NotNull Collection<Head> getHeads() {
        return Collections.unmodifiableCollection(heads.values());
    }

    public void loadHeads() {
        headRepository.loadHeads().thenAccept(loadedHeads -> {
            heads.clear();
            for (Head head : loadedHeads) {
                Position pos = PositionAdapter.convert(head.getLocation());
                heads.put(pos, head);
            }
        });
    }
}
