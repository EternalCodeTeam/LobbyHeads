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
    public void serialize(Head head, SerializationData data, GenericsDeclaration generics) {
        data.add("position", head.getPosition());
        data.add("player", head.getPlayerName());
        data.add("uuid", head.getPlayerUUID().toString());
    }

    @Override
    public Head deserialize(DeserializationData data, GenericsDeclaration generics) {
        Position position = data.get("position", Position.class);
        String playerName = data.get("player", String.class);
        UUID playerUUID = data.get("uuid", UUID.class);

        return new Head(position, playerName, playerUUID);
    }
}
