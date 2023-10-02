package com.eternalcode.lobbyheads.head;

import com.eternalcode.lobbyheads.configuration.ConfigurationService;
import com.eternalcode.lobbyheads.configuration.implementation.HeadsConfiguration;
import com.eternalcode.lobbyheads.head.HeadInfo;
import com.eternalcode.lobbyheads.head.HeadService;
import com.eternalcode.lobbyheads.notification.NotificationAnnouncer;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.List;

public class HeadCommand implements CommandExecutor, TabCompleter {

    private final HeadsConfiguration headsConfiguration;
    private final HeadService headService;
    private final ConfigurationService configurationService;
    private final NotificationAnnouncer notificationAnnouncer;

    public HeadCommand(HeadsConfiguration headsConfiguration, HeadService headService, ConfigurationService configurationService, NotificationAnnouncer notificationAnnouncer) {
        this.headsConfiguration = headsConfiguration;
        this.headService = headService;
        this.configurationService = configurationService;
        this.notificationAnnouncer = notificationAnnouncer;
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
            case "add" -> this.createHead(player);
            case "remove" -> this.removeHead(player);
            case "reload" -> {
                this.configurationService.reload();
                this.notificationAnnouncer.sendMessage(player, this.headsConfiguration.messages.configurationReloaded);
            }
            default -> this.notificationAnnouncer.sendMessage(player, invalidUsage);
        }
        return true;
    }

    void removeHead(Player player) {
        Block block = player.getTargetBlock(null, 5);

        for (HeadInfo headInfo : this.headsConfiguration.heads) {
            if (headInfo.getLocation().equals(block.getLocation())) {
                this.headService.removeHologram(headInfo);
                this.headsConfiguration.heads.remove(headInfo);
                this.headsConfiguration.save();
                this.notificationAnnouncer.sendMessage(player, this.headsConfiguration.messages.headRemoved);

                return;
            }
        }
    }

    void createHead(Player player) {
        Block block = player.getTargetBlock(null, 5);

        if (block.getType() != Material.PLAYER_HEAD) {
            this.notificationAnnouncer.sendMessage(player, this.headsConfiguration.messages.youAreNotLookingAtHead);
            return;
        }

        if (this.headService.find(block.getLocation()).isPresent()) {
            this.notificationAnnouncer.sendMessage(player, this.headsConfiguration.messages.headAlreadyExists);
            return;
        }

        HeadInfo headInfo = new HeadInfo(block.getLocation(), player.getName(), player.getUniqueId());

        this.headService.createHologram(player, headInfo, this.headsConfiguration.head.headFormat);
        this.headsConfiguration.heads.add(headInfo);
        this.headsConfiguration.save();
        this.notificationAnnouncer.sendMessage(player, this.headsConfiguration.messages.headAdded);
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