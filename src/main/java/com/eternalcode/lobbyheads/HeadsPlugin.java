package com.eternalcode.lobbyheads;

import com.eternalcode.lobbyheads.adventure.AdventureLegacyColorProcessor;
import com.eternalcode.lobbyheads.configuration.ConfigurationService;
import com.eternalcode.lobbyheads.configuration.implementation.HeadsConfiguration;
import com.eternalcode.lobbyheads.reload.ReloadService;
import com.eternalcode.lobbyheads.event.EventCaller;
import com.eternalcode.lobbyheads.head.HeadController;
import com.eternalcode.lobbyheads.head.HeadManager;
import com.eternalcode.lobbyheads.head.HeadRepository;
import com.eternalcode.lobbyheads.head.HeadRepositoryImpl;
import com.eternalcode.lobbyheads.head.block.BlockController;
import com.eternalcode.lobbyheads.head.block.BlockService;
import com.eternalcode.lobbyheads.head.command.HeadCommand;
import com.eternalcode.lobbyheads.head.hologram.HologramController;
import com.eternalcode.lobbyheads.head.hologram.HologramService;
import com.eternalcode.lobbyheads.head.particle.ParticleController;
import com.eternalcode.lobbyheads.head.sound.SoundController;
import com.eternalcode.lobbyheads.notification.NotificationAnnouncer;
import com.eternalcode.lobbyheads.updater.UpdaterNotificationController;
import com.eternalcode.lobbyheads.updater.UpdaterService;
import dev.rollczi.liteskullapi.LiteSkullFactory;
import dev.rollczi.liteskullapi.SkullAPI;
import net.kyori.adventure.platform.AudienceProvider;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bstats.bukkit.Metrics;
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
        UpdaterService updaterService = new UpdaterService(this.getDescription());


        HeadRepository headRepository = new HeadRepositoryImpl(config, configurationService);

        this.headManager = new HeadManager(eventCaller, headRepository);
        this.headManager.loadHeads();

        HologramService hologramService = new HologramService(this, config, miniMessage, server, this.headManager);
        hologramService.loadHolograms();

        BlockService blockService = new BlockService(config, notificationAnnouncer, this.headManager);

        ReloadService reloadService = new ReloadService()
            .register(configurationService)
            .register(hologramService);

        this.getCommand("heads").setExecutor(new HeadCommand(config, notificationAnnouncer, blockService, reloadService));

        Stream.of(
            new HeadController(config, this.headManager, notificationAnnouncer),

            // sub-controllers
            new SoundController(server, config),
            new ParticleController(server, config),
            new BlockController(this, server.getScheduler(), this.skullAPI),
            new HologramController(hologramService, config, server),

            // check-up-to-date
            new UpdaterNotificationController(updaterService, config, notificationAnnouncer)
        ).forEach(listener -> server.getPluginManager().registerEvents(listener, this));

        new Metrics(this, 20048);
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
