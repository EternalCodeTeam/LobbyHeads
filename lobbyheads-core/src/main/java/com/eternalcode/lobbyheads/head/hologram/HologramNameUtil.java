package com.eternalcode.lobbyheads.head.hologram;

import com.eternalcode.lobbyheads.position.Position;

class HologramNameUtil {

    private static final String HOLOGRAM_NAME_PREFIX = "heads_%s_%s_%s_%s";

    static String generateHologramName(Position position) {
        String world = sanitize(position.world());
        String x = sanitize(String.valueOf(position.x()));
        String y = sanitize(String.valueOf(position.y()));
        String z = sanitize(String.valueOf(position.z()));
        return String.format(HOLOGRAM_NAME_PREFIX, world, x, y, z);
    }

    private static String sanitize(String input) {
        return input.replaceAll("[^a-zA-Z0-9_-]", "_");
    }
}
