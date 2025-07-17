package com.eternalcode.lobbyheads.head.command;

import com.eternalcode.lobbyheads.configuration.implementation.HeadsConfiguration;
import com.eternalcode.lobbyheads.head.block.BlockService;
import com.eternalcode.lobbyheads.notification.NotificationAnnouncer;
import com.eternalcode.lobbyheads.reload.ReloadService;
import java.util.Locale;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class HeadCommand implements CommandExecutor {

    private static final String HEAD_MANAGEMENT_PERMISSION = "lobbyheads.admin";

    private final HeadsConfiguration config;
    private final NotificationAnnouncer announcer;
    private final BlockService blockService;
    private final ReloadService reloadService;

    public HeadCommand(
        HeadsConfiguration config,
        NotificationAnnouncer announcer,
        BlockService blockService,
        ReloadService reloadService
    ) {
        this.config = config;
        this.announcer = announcer;
        this.blockService = blockService;
        this.reloadService = reloadService;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!canUse(sender)) {
            announcer.sendMessage(sender, config.messages.noPermissionCommand);
            return true;
        }

        if (!(sender instanceof Player player)) {
            announcer.sendMessage(sender, config.messages.onlyForPlayers);
            return true;
        }

        if (args.length < 1) {
            announcer.sendMessage(player, config.messages.invalidCommand);
            return true;
        }

        String subcommand = args[0].toLowerCase(Locale.ROOT);

        switch (subcommand) {
            case "add" -> {
                Block targetBlock = player.getTargetBlockExact(5);
                if (targetBlock == null || targetBlock.getType() == Material.AIR) {
                    announcer.sendMessage(player, config.messages.noBlockInSight);
                    return true;
                }
                blockService.createHeadBlock(targetBlock.getLocation(), player);
                return true;
            }
            case "remove" -> {
                Block targetBlock = player.getTargetBlockExact(5);
                if (targetBlock == null || targetBlock.getType() == Material.AIR) {
                    announcer.sendMessage(player, config.messages.noBlockInSight);
                    return true;
                }
                blockService.removeHeadBlock(targetBlock.getLocation(), player);
                return true;
            }
            case "reload" -> {
                reloadService.reload();
                announcer.sendMessage(player, config.messages.configurationReloaded);
                return true;
            }
            default -> {
                announcer.sendMessage(player, config.messages.invalidCommand);
                return true;
            }
        }
    }

    private boolean canUse(CommandSender sender) {
        return sender.hasPermission(HEAD_MANAGEMENT_PERMISSION);
    }
}
