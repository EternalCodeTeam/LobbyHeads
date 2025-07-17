package com.eternalcode.lobbyheads.configuration.migration;

import eu.okaeri.configs.migrate.ConfigMigration;
import eu.okaeri.configs.migrate.builtin.NamedMigration;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class HeadsPositionMigration_2025_07_17 extends NamedMigration {

    private static final Pattern POSITION_PATTERN = Pattern.compile(
            "Position\\{x=([^,]+), y=([^,]+), z=([^,]+), world='([^']+)'\\}"
    );

    public HeadsPositionMigration_2025_07_17() {
        super(
                "Migrate heads position format from old string-based to new structured format",
                migrateHeadsPosition()
        );
    }

    private static ConfigMigration migrateHeadsPosition() {
        return (config, view) -> {
            if (!view.exists("heads")) {
                return false;
            }

            Object headsValue = view.get("heads");
            if (!(headsValue instanceof List)) {
                return false;
            }

            @SuppressWarnings("unchecked")
            List<Map<String, Object>> oldHeads = (List<Map<String, Object>>) headsValue;
            List<Map<String, Object>> newHeads = new ArrayList<>();

            boolean migrated = false;

            for (Map<String, Object> oldHead : oldHeads) {
                Object positionValue = oldHead.get("position");
                Object playerValue = oldHead.get("player");
                Object uuidValue = oldHead.get("uuid");

                if (positionValue instanceof String positionString) {
                    Matcher matcher = POSITION_PATTERN.matcher(positionString);

                    if (matcher.matches()) {
                        Map<String, Object> newHead = new LinkedHashMap<>();

                        // Extract world from old position string
                        String world = matcher.group(4);
                        newHead.put("world", world);

                        // Extract and parse coordinates
                        try {
                            double x = Double.parseDouble(matcher.group(1));
                            double y = Double.parseDouble(matcher.group(2));
                            double z = Double.parseDouble(matcher.group(3));

                            newHead.put("x", x);
                            newHead.put("y", y);
                            newHead.put("z", z);
                        }
                        catch (NumberFormatException e) {
                            // If parsing fails, use defaults
                            newHead.put("x", 0.0);
                            newHead.put("y", 0.0);
                            newHead.put("z", 0.0);
                        }

                        // Set default yaw and pitch
                        newHead.put("yaw", 0.0F);
                        newHead.put("pitch", 0.0F);

                        // Migrate player name and UUID
                        newHead.put("playerName", playerValue);
                        newHead.put("playerUUID", uuidValue);

                        newHeads.add(newHead);
                        migrated = true;
                    }
                    else {
                        // If position string doesn't match pattern, create default entry
                        Map<String, Object> defaultHead = createDefaultHead();
                        defaultHead.put("playerName", playerValue);
                        defaultHead.put("playerUUID", uuidValue);
                        newHeads.add(defaultHead);
                        migrated = true;
                    }
                }
                else {
                    // If position is not a string, it might already be in new format
                    // or it's corrupted - add as is or create default
                    if (oldHead.containsKey("world") && oldHead.containsKey("x") &&
                            oldHead.containsKey("y") && oldHead.containsKey("z")) {
                        // Already in new format
                        newHeads.add(oldHead);
                    }
                    else {
                        // Create default entry
                        Map<String, Object> defaultHead = createDefaultHead();
                        defaultHead.put("playerName", playerValue);
                        defaultHead.put("playerUUID", uuidValue);
                        newHeads.add(defaultHead);
                        migrated = true;
                    }
                }
            }

            if (migrated) {
                view.set("heads", newHeads);
                return true;
            }

            return false;
        };
    }

    private static Map<String, Object> createDefaultHead() {
        Map<String, Object> defaultHead = new LinkedHashMap<>();
        defaultHead.put("world", null);
        defaultHead.put("x", 0.0);
        defaultHead.put("y", 0.0);
        defaultHead.put("z", 0.0);
        defaultHead.put("yaw", 0.0F);
        defaultHead.put("pitch", 0.0F);
        defaultHead.put("playerName", null);
        defaultHead.put("playerUUID", null);
        return defaultHead;
    }
}
