package com.eternalcode.lobbyheads.head;

import java.util.Collection;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public interface HeadManager {

    void addHead(Player player, Location location);

    void removeHead(Location location);

    void updateHead(Player player, Location location);

    Head getHead(Location location);

    Collection<Head> getHeads();
}
