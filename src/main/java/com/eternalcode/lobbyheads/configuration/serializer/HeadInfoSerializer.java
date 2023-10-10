package com.eternalcode.lobbyheads.configuration.serializer;

import com.eternalcode.lobbyheads.position.Position;
import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;

import java.util.UUID;

public class HeadInfoSerializer implements ObjectSerializer<HeadInfo> {

    @Override
    public boolean supports(Class<? super HeadInfo> type) {
        return HeadInfo.class.isAssignableFrom(type);
    }

    @Override
    public void serialize(HeadInfo headInfo, SerializationData data, GenericsDeclaration generics) {
        Position position = headInfo.getPosition();
        data.add("position", position);
        data.add("player", headInfo.getPlayerName());
        data.add("uuid", headInfo.getPlayerUUID().toString());
    }

    @Override
    public HeadInfo deserialize(DeserializationData data, GenericsDeclaration generics) {
        Position position = data.get("position", Position.class);

        return new HeadInfo(position, data.get("player", String.class), UUID.fromString(data.get("uuid", String.class)));
    }
}
