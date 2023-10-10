package com.eternalcode.lobbyheads;

import com.eternalcode.lobbyheads.adventure.AdventureLegacyColorProcessor;
import com.eternalcode.lobbyheads.configuration.ConfigurationService;
import com.eternalcode.lobbyheads.configuration.implementation.HeadsConfiguration;
import com.eternalcode.lobbyheads.event.EventCaller;
import com.eternalcode.lobbyheads.head.HeadController;
import com.eternalcode.lobbyheads.head.HeadManager;
import com.eternalcode.lobbyheads.head.block.HeadBlockController;
import com.eternalcode.lobbyheads.head.command.HeadCommand;
import com.eternalcode.lobbyheads.head.hologram.HologramController;
import com.eternalcode.lobbyheads.head.hologram.HologramService;
import com.eternalcode.lobbyheads.head.particle.ParticleController;
import com.eternalcode.lobbyheads.head.sound.SoundController;
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
import java.util.stream.Stream;

public class HeadsPlugin extends JavaPlugin {

    private SkullAPI skullAPI;
    private AudienceProvider audienceProvider;
    private HeadManager headManager;

    @Override
    public void onEnable() {
        Server server = this.getServer();

        ConfigurationService configurationService = new ConfigurationService();
        HeadsConfiguration config = configurationService.create(HeadsConfiguration.class, new File(this.getDataFolder(), "config.yml"));

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

        this.headManager = new HeadManager(eventCaller, config);
        HologramService hologramService = new HologramService(this, config, miniMessage, server);
        hologramService.loadHolograms();


        this.getCommand("heads").setExecutor(new HeadCommand(config, configurationService, notificationAnnouncer, this.headManager));

        Stream.of(
            new HeadController(config, this.headManager, notificationAnnouncer),

            // sub-controllers
            new SoundController(server, config),
            new ParticleController(server, config),
            new HeadBlockController(this, server.getScheduler(), this.skullAPI),
            new HologramController(hologramService, config, server)
        ).forEach(listener -> server.getPluginManager().registerEvents(listener, this));
    }

    @Override
    public void onDisable() {
        if (this.headManager != null) {
            this.headManager.clearHeads();
        }

        if (this.skullAPI != null) {
            this.skullAPI.shutdown();
        }

        if (this.audienceProvider != null) {
            this.audienceProvider.close();
        }
    }

}
