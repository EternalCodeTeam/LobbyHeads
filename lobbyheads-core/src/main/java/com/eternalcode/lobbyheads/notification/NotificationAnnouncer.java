package com.eternalcode.lobbyheads.notification;

import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.CommandSender;

public final class NotificationAnnouncer {

    private final MiniMessage miniMessage;

    public NotificationAnnouncer(MiniMessage miniMessage) {
        this.miniMessage = miniMessage;
    }

    public void sendMessage(CommandSender sender, String text) {
        sender.sendMessage(this.miniMessage.deserialize(text));
    }
}
