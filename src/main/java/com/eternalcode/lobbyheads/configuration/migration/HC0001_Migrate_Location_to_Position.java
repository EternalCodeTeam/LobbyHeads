package com.eternalcode.lobbyheads.configuration.migration;

import com.eternalcode.lobbyheads.configuration.shared.Position;
import eu.okaeri.configs.migrate.builtin.NamedMigration;

public class HC0001_Migrate_Location_to_Position extends NamedMigration {

    public HC0001_Migrate_Location_to_Position() {
        super(
            "Migrate old Location to new Position",
            (config, view) -> {
                if (view.exists("world") &&
                    view.exists("x") &&
                    view.exists("y") &&
                    view.exists("z")) {

                    double x = (double) view.get("x");
                    double y = (double) view.get("y");
                    double z = (double) view.get("z");
                    String world = (String) view.remove("world");

                    Position position = new Position(x, y, z, 0, 0, world);
                    view.remove("x");
                    view.remove("y");
                    view.remove("z");

                    view.set("position", position);

                    return true;
                }
                return false;
            }
        );
    }

}