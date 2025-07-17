package com.eternalcode.lobbyheads.configuration.serializer;

import com.cryptomorin.xseries.XSound;
import eu.okaeri.configs.schema.GenericsDeclaration;
import eu.okaeri.configs.serdes.DeserializationData;
import eu.okaeri.configs.serdes.ObjectSerializer;
import eu.okaeri.configs.serdes.SerializationData;

public class SoundSerializer implements ObjectSerializer<XSound> {

    @Override
    public boolean supports(Class<? super XSound> type) {
        return XSound.class.isAssignableFrom(type);
    }

    @Override
    public void serialize(
        XSound object,
        SerializationData data,
        GenericsDeclaration generics
    ) {
        data.setValue(object.name());
    }

    @Override
    public XSound deserialize(DeserializationData data, GenericsDeclaration generics) {
        String value = data.getValue(String.class);
        return XSound.of(value).orElseThrow(() -> new IllegalArgumentException("Unknown sound: " + value));
    }
}
