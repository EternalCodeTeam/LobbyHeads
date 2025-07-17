package com.eternalcode.lobbyheads.configuration.migration;

import static eu.okaeri.configs.migrate.ConfigMigrationDsl.move;

import eu.okaeri.configs.migrate.builtin.NamedMigration;

public class HeadsConfigMigration_2025_07_17 extends NamedMigration {

    public HeadsConfigMigration_2025_07_17() {
        super(
                "Rename config keys to improved camelCase names",
                // top‑level sections
                move("headSection", "headSettings"),

                // message keys
                move("messages.commandInvalidUsage", "messages.invalidCommand"),
                move("messages.configurationReloaded", "messages.configReloaded"),
                move("messages.headAlreadyExists", "messages.headExists"),
                move("messages.headAdded", "messages.headAdded"),
                move("messages.headRemoved", "messages.headRemoved"),
                move("messages.onlyForPlayers", "messages.playersOnly"),
                move("messages.playerAlreadyReplaceThisHead", "messages.alreadyReplaced"),
                move("messages.playerMustWaitToReplaceHead", "messages.replaceCooldown"),
                move("messages.playerNotLookingAtHead", "messages.notLookingAtHead"),
                move("messages.playerNotPermittedToReplaceHeads", "messages.noPermissionReplace"),
                move("messages.playerNotPermittedToUseThisCommand", "messages.noPermissionCommand"),
                move("messages.noBlockInSight", "messages.noBlockInSightMessage")
        );
    }
}
