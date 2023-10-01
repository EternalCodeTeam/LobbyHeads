package com.eternalcode.lobbyheads.configuration.serializer;

import com.eternalcode.lobbyheads.head.HeadInfo;
import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.UUID;

public class HeadInfoSerializer implements ObjectSerializer<HeadInfo> {

    @Override
    public boolean supports(Class<? super HeadInfo> type) {
        return HeadInfo.class.isAssignableFrom(type);
    }

    public void serialize(HeadInfo headInfo, SerializationData data, GenericsDeclaration generics) {
        data.add("world", headInfo.getLocation().getWorld().getName());
        data.add("x", headInfo.getLocation().getX());
        data.add("y", headInfo.getLocation().getY());
        data.add("z", headInfo.getLocation().getZ());
        data.add("player", headInfo.getPlayerName());
        data.add("uuid", headInfo.getPlayerUUID().toString());
    }

    @Override
    public HeadInfo deserialize(DeserializationData data, GenericsDeclaration generics) {
        Location location = new Location(
            Bukkit.getWorld(data.get("world", String.class)),
            data.get("x", Double.class),
            data.get("y", Double.class),
            data.get("z", Double.class)
        );

        return new HeadInfo(location, data.get("player", String.class), UUID.fromString(data.get("uuid", String.class)));
    }
}
