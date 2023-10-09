package com.eternalcode.lobbyheads;

import com.eternalcode.lobbyheads.adventure.AdventureLegacyColorProcessor;
import com.eternalcode.lobbyheads.configuration.ConfigurationService;
import com.eternalcode.lobbyheads.configuration.implementation.HeadsConfiguration;
import com.eternalcode.lobbyheads.event.EventCaller;
import com.eternalcode.lobbyheads.head.HeadBlockService;
import com.eternalcode.lobbyheads.head.HeadCommand;
import com.eternalcode.lobbyheads.head.HeadController;
import com.eternalcode.lobbyheads.head.HeadService;
import com.eternalcode.lobbyheads.notification.NotificationAnnouncer;
import dev.rollczi.liteskullapi.LiteSkullFactory;
import dev.rollczi.liteskullapi.SkullAPI;
import net.kyori.adventure.platform.AudienceProvider;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Server;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.time.Duration;

public class HeadsPlugin extends JavaPlugin {

    private SkullAPI skullAPI;
    private AudienceProvider audienceProvider;

    @Override
    public void onEnable() {
        Server server = this.getServer();

        ConfigurationService configurationService = new ConfigurationService();
        HeadsConfiguration headsConfiguration = configurationService.create(HeadsConfiguration.class, new File(this.getDataFolder(), "config.yml"));

        EventCaller eventCaller = new EventCaller(server);

        this.skullAPI = LiteSkullFactory.builder()
            .cacheExpireAfterWrite(Duration.ofHours(1L))
            .bukkitScheduler(this)
            .build();

        this.audienceProvider = BukkitAudiences.create(this);
        MiniMessage miniMessage = MiniMessage.builder()
            .postProcessor(new AdventureLegacyColorProcessor())
            .build();

        NotificationAnnouncer notificationAnnouncer = new NotificationAnnouncer(this.audienceProvider, miniMessage);

        HeadService headService = new HeadService(this.skullAPI, this, headsConfiguration, miniMessage, server);
        headService.loadHolograms();

        HeadBlockService headBlockService = new HeadBlockService(headsConfiguration, headService, notificationAnnouncer);

        this.getCommand("heads").setExecutor(new HeadCommand(headsConfiguration, configurationService, notificationAnnouncer, headBlockService));
        this.getServer().getPluginManager().registerEvents(new HeadController(headsConfiguration, headService, notificationAnnouncer, headBlockService), this);
    }

    @Override
    public void onDisable() {
        if (this.skullAPI != null) {
            this.skullAPI.shutdown();
        }

        if (this.audienceProvider != null) {
            this.audienceProvider.close();
        }
    }

}
