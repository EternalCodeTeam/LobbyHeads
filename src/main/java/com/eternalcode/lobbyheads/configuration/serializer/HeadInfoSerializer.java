package com.eternalcode.lobbyheads.configuration.serializer;

import com.eternalcode.lobbyheads.configuration.shared.Position;
import com.eternalcode.lobbyheads.configuration.shared.PositionAdapter;
import com.eternalcode.lobbyheads.head.HeadInfo;
import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;
import org.bukkit.Location;

import java.util.UUID;

public class HeadInfoSerializer implements ObjectSerializer<HeadInfo> {

    @Override
    public boolean supports(Class<? super HeadInfo> type) {
        return HeadInfo.class.isAssignableFrom(type);
    }

    @Override
    public void serialize(HeadInfo headInfo, SerializationData data, GenericsDeclaration generics) {
        Position position = PositionAdapter.convert(headInfo.getLocation());
        data.add("position", position);
        data.add("player", headInfo.getPlayerName());
        data.add("uuid", headInfo.getPlayerUUID().toString());
    }

    @Override
    public HeadInfo deserialize(DeserializationData data, GenericsDeclaration generics) {
        Position position = data.get("position", Position.class);
        Location location = PositionAdapter.convert(position);

        return new HeadInfo(location, data.get("player", String.class), UUID.fromString(data.get("uuid", String.class)));
    }
}