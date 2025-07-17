package com.eternalcode.lobbyheads.head.hologram;

import com.eternalcode.commons.bukkit.position.Position;

class HologramNameUtil {

    private static final String HOLOGRAM_NAME_TEMPLATE = "heads_%s_%s_%s_%s";

    static String generateHologramName(Position position) {
        return String.format(
            HOLOGRAM_NAME_TEMPLATE,
            sanitize(position.world()),
            sanitize(String.valueOf(position.x())),
            sanitize(String.valueOf(position.y())),
            sanitize(String.valueOf(position.z()))
        );
    }

    private static String sanitize(String input) {
        return input.replaceAll("[^a-zA-Z0-9_-]", "_");
    }
}
