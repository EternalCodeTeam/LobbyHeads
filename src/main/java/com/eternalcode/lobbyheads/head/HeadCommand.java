package com.eternalcode.lobbyheads.head;

import com.eternalcode.lobbyheads.configuration.ConfigurationService;
import com.eternalcode.lobbyheads.configuration.implementation.HeadsConfiguration;
import com.eternalcode.lobbyheads.notification.NotificationAnnouncer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.List;

public class HeadCommand implements CommandExecutor, TabCompleter {

    private static final String HEAD_MANAGEMENT_PERMISSION = "lobbyheads.admin";

    private final HeadsConfiguration config;
    private final ConfigurationService configurationService;
    private final NotificationAnnouncer notificationAnnouncer;
    private final HeadBlockService headBlockService;

    public HeadCommand(HeadsConfiguration config, ConfigurationService configurationService, NotificationAnnouncer notificationAnnouncer, HeadBlockService headBlockService) {
        this.config = config;
        this.configurationService = configurationService;
        this.notificationAnnouncer = notificationAnnouncer;
        this.headBlockService = headBlockService;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission(HEAD_MANAGEMENT_PERMISSION)) {
            this.notificationAnnouncer.sendMessage(sender, this.config.messages.youAreNotPermittedToUseThisCommand);
            return false;
        }

        if (!(sender instanceof Player player)) {
            this.notificationAnnouncer.sendMessage(sender, this.config.messages.onlyForPlayers);
            return true;
        }

        String invalidUsage = this.config.messages.invalidUsage;

        if (args.length < 1) {
            this.notificationAnnouncer.sendMessage(player, invalidUsage);
            return true;
        }

        String subcommand = args[0].toLowerCase();

        switch (subcommand) {
            case "add" -> this.headBlockService.createHead(player);
            case "remove" -> this.headBlockService.removeHead(player);
            case "reload" -> {
                this.configurationService.reload();
                this.notificationAnnouncer.sendMessage(player, this.config.messages.configurationReloaded);
            }
            default -> this.notificationAnnouncer.sendMessage(player, invalidUsage);
        }
        return true;
    }


    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 1 && sender.hasPermission(HEAD_MANAGEMENT_PERMISSION)) {
            return List.of("add", "remove", "reload");
        }

        return List.of();
    }
}