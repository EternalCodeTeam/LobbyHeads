package com.eternalcode.lobbyheads.head.modern.command;

import com.eternalcode.lobbyheads.configuration.ConfigurationService;
import com.eternalcode.lobbyheads.configuration.implementation.HeadsConfiguration;
import com.eternalcode.lobbyheads.head.modern.Head;
import com.eternalcode.lobbyheads.head.modern.HeadManager;
import com.eternalcode.lobbyheads.notification.NotificationAnnouncer;
import com.eternalcode.lobbyheads.position.Position;
import com.eternalcode.lobbyheads.position.PositionAdapter;
import org.bukkit.Location;
import org.bukkit.block.Block;
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
    private final HeadManager headManager;

    public HeadCommand(HeadsConfiguration config, ConfigurationService configurationService, NotificationAnnouncer notificationAnnouncer, HeadManager headManager) {
        this.config = config;
        this.configurationService = configurationService;
        this.notificationAnnouncer = notificationAnnouncer;
        this.headManager = headManager;
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

        Block block = player.getTargetBlock(null, 5);
        Location location = block.getLocation();
        Position convert = PositionAdapter.convert(location);

        Head head = this.headManager.getHead(convert);

        if (head == null) {
            this.notificationAnnouncer.sendMessage(player, this.config.messages.youAreNotLookingAtHead);
            return true;
        }

        switch (subcommand) {
            case "add" -> this.headManager.addHead(player, convert);
            case "remove" -> this.headManager.removeHead(convert);
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
