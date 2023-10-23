package com.eternalcode.lobbyheads.head.command;

import com.eternalcode.lobbyheads.configuration.implementation.HeadsConfiguration;
import com.eternalcode.lobbyheads.head.block.BlockService;
import com.eternalcode.lobbyheads.notification.NotificationAnnouncer;
import com.eternalcode.lobbyheads.position.Position;
import com.eternalcode.lobbyheads.position.PositionAdapter;
import com.eternalcode.lobbyheads.configuration.reload.ReloadService;
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
    private final NotificationAnnouncer notificationAnnouncer;
    private final BlockService blockService;
    private final ReloadService reloadService;

    public HeadCommand(HeadsConfiguration config, NotificationAnnouncer notificationAnnouncer,
                       BlockService blockService, ReloadService reloadService) {
        this.config = config;
        this.notificationAnnouncer = notificationAnnouncer;
        this.blockService = blockService;
        this.reloadService = reloadService;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission(HEAD_MANAGEMENT_PERMISSION)) {
            this.notificationAnnouncer.sendMessage(sender, this.config.messages.playerNotPermittedToUseThisCommand);
            return false;
        }

        if (!(sender instanceof Player player)) {
            this.notificationAnnouncer.sendMessage(sender, this.config.messages.onlyForPlayers);
            return true;
        }

        String invalidUsage = this.config.messages.commandInvalidUsage;

        if (args.length < 1) {
            this.notificationAnnouncer.sendMessage(player, invalidUsage);
            return true;
        }

        String subcommand = args[0].toLowerCase();

        Block block = player.getTargetBlock(null, 5);
        Location location = block.getLocation();
        Position convert = PositionAdapter.convert(location);

        switch (subcommand) {
            case "add" -> this.blockService.createHeadBlock(location, player, convert);
            case "remove" -> this.blockService.removeHeadBlock(convert, player);
            case "reload" -> {
                this.reloadService.reload();
                this.notificationAnnouncer.sendMessage(player, this.config.messages.configurationReloaded);
            }
            default -> this.notificationAnnouncer.sendMessage(player, invalidUsage);
        }
        return true;
    }


    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (sender.hasPermission(HEAD_MANAGEMENT_PERMISSION)) {
            return List.of("add", "remove", "reload");
        }

        return List.of();
    }
}
