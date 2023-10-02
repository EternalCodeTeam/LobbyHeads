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

    private final HeadsConfiguration headsConfiguration;
    private final ConfigurationService configurationService;
    private final NotificationAnnouncer notificationAnnouncer;
    private final HeadBlockService headBlockService;

    public HeadCommand(HeadsConfiguration headsConfiguration, ConfigurationService configurationService, NotificationAnnouncer notificationAnnouncer, HeadBlockService headBlockService) {
        this.headsConfiguration = headsConfiguration;
        this.configurationService = configurationService;
        this.notificationAnnouncer = notificationAnnouncer;
        this.headBlockService = headBlockService;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("lobbyheads.admin")) {
            this.notificationAnnouncer.sendMessage(sender, this.headsConfiguration.messages.youAreNotPermittedToUseThisCommand);
            return false;
        }

        if (!(sender instanceof Player player)) {
            this.notificationAnnouncer.sendMessage(sender, this.headsConfiguration.messages.onlyForPlayers);
            return true;
        }

        String invalidUsage = this.headsConfiguration.messages.invalidUsage;

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
                this.notificationAnnouncer.sendMessage(player, this.headsConfiguration.messages.configurationReloaded);
            }
            default -> this.notificationAnnouncer.sendMessage(player, invalidUsage);
        }
        return true;
    }


    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        return List.of(
            "add",
            "remove",
            "reload"
        );
    }
}