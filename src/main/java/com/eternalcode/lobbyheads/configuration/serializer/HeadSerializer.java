package com.eternalcode.lobbyheads.configuration.serializer;

import com.eternalcode.lobbyheads.head.Head;
import com.eternalcode.lobbyheads.position.Position;
import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;

import java.util.UUID;

public class HeadSerializer implements ObjectSerializer<Head> {

    @Override
    public boolean supports(Class<? super Head> type) {
        return Head.class.isAssignableFrom(type);
    }

    @Override
    public void serialize(Head headInfo, SerializationData data, GenericsDeclaration generics) {
        Position position = headInfo.getPosition();
        data.add("position", position);
        data.add("player", headInfo.getPlayerName());
        data.add("uuid", headInfo.getPlayerUUID().toString());
    }

    @Override
    public Head deserialize(DeserializationData data, GenericsDeclaration generics) {
        Position position = data.get("position", Position.class);

        return new Head(position, data.get("player", String.class), UUID.fromString(data.get("uuid", String.class)));
    }
}
