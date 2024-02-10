package com.eternalcode.lobbyheads.updater;

import com.eternalcode.lobbyheads.configuration.implementation.HeadsConfiguration;
import com.eternalcode.lobbyheads.notification.NotificationAnnouncer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.concurrent.CompletableFuture;

public class UpdaterNotificationController implements Listener {

    private static final String NEW_VERSION_AVAILABLE = "<b><gradient:#ff14dc:#c814ff>LobbyHeads:</gradient></b> <color:#ff00e1>New version of LobbyHeads is available, please update!";

    private final UpdaterService updaterService;
    private final HeadsConfiguration config;
    private final NotificationAnnouncer notificationAnnouncer;

    public UpdaterNotificationController(UpdaterService updaterService, HeadsConfiguration config,
                                         NotificationAnnouncer notificationAnnouncer) {
        this.updaterService = updaterService;
        this.config = config;
        this.notificationAnnouncer = notificationAnnouncer;
    }

    @EventHandler
    void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (!player.hasPermission("lobbyheads.receiveupdates") || !this.config.receivePluginUpdates) {
            return;
        }

        CompletableFuture<Boolean> upToDate = this.updaterService.isUpToDate();

        upToDate.whenComplete((isUpToDate, throwable) -> {
            if (throwable != null) {
                throwable.printStackTrace();

                return;
            }

            if (!isUpToDate) {
                this.notificationAnnouncer.sendMessage(player, NEW_VERSION_AVAILABLE);
            }
        });
    }

}
