package com.eternalcode.lobbyheads.adventure;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public final class AdventureLegacy {

    public static final LegacyComponentSerializer SECTION_SERIALIZER = LegacyComponentSerializer.builder()
        .character('§')
        .hexColors()
        .useUnusualXRepeatedCharacterHexFormat()
        .build();

    private static final LegacyComponentSerializer LEGACY_SERIALIZER = LegacyComponentSerializer.builder()
        .character('&')
        .hexColors()
        .useUnusualXRepeatedCharacterHexFormat()
        .build();

    private AdventureLegacy() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static Component component(String text) {
        return LEGACY_SERIALIZER.deserialize(text);
    }
}