package com.eternalcode.lobbyheads;

import com.eternalcode.commons.adventure.AdventureLegacyColorPostProcessor;
import com.eternalcode.commons.adventure.AdventureLegacyColorPreProcessor;
import com.eternalcode.lobbyheads.configuration.ConfigurationService;
import com.eternalcode.lobbyheads.configuration.implementation.HeadsConfiguration;
import com.eternalcode.lobbyheads.event.EventCaller;
import com.eternalcode.lobbyheads.head.HeadController;
import com.eternalcode.lobbyheads.head.HeadManager;
import com.eternalcode.lobbyheads.head.HeadManagerImpl;
import com.eternalcode.lobbyheads.head.HeadRepository;
import com.eternalcode.lobbyheads.head.HeadRepositoryImpl;
import com.eternalcode.lobbyheads.head.block.BlockController;
import com.eternalcode.lobbyheads.head.block.BlockService;
import com.eternalcode.lobbyheads.head.command.HeadCommand;
import com.eternalcode.lobbyheads.head.hologram.HologramController;
import com.eternalcode.lobbyheads.head.hologram.HologramService;
import com.eternalcode.lobbyheads.head.hologram.provider.HologramProvider;
import com.eternalcode.lobbyheads.head.hologram.provider.HologramProviderPicker;
import com.eternalcode.lobbyheads.head.particle.ParticleController;
import com.eternalcode.lobbyheads.head.sound.SoundController;
import com.eternalcode.lobbyheads.notification.NotificationAnnouncer;
import com.eternalcode.lobbyheads.reload.ReloadService;
import com.eternalcode.lobbyheads.updater.UpdaterNotificationController;
import com.eternalcode.lobbyheads.updater.UpdaterService;
import dev.rollczi.liteskullapi.LiteSkullFactory;
import dev.rollczi.liteskullapi.SkullAPI;
import java.io.File;
import java.time.Duration;
import java.util.stream.Stream;
import net.kyori.adventure.platform.AudienceProvider;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bstats.bukkit.Metrics;
import org.bukkit.Server;
import org.bukkit.plugin.java.JavaPlugin;

public class HeadsPlugin extends JavaPlugin implements LobbyHeadsApi {

    private SkullAPI skullAPI;
    private AudienceProvider audienceProvider;
    private HeadManagerImpl headManagerImpl;

    @Override
    public void onEnable() {
        Server server = this.getServer();

        ConfigurationService configurationService = new ConfigurationService();
        HeadsConfiguration config =
            configurationService.create(HeadsConfiguration.class, new File(this.getDataFolder(), "config.yml"));

        EventCaller eventCaller = new EventCaller(server);

        this.skullAPI = LiteSkullFactory.builder()
            .cacheExpireAfterWrite(Duration.ofHours(1L))
            .bukkitScheduler(this)
            .build();

        this.audienceProvider = BukkitAudiences.create(this);
        MiniMessage miniMessage = MiniMessage.builder()
            .postProcessor(new AdventureLegacyColorPostProcessor())
            .preProcessor(new AdventureLegacyColorPreProcessor())
            .build();

        NotificationAnnouncer notificationAnnouncer = new NotificationAnnouncer(this.audienceProvider, miniMessage);
        UpdaterService updaterService = new UpdaterService(this.getDescription());

        HeadRepository headRepository = new HeadRepositoryImpl(config, configurationService);

        this.headManagerImpl = new HeadManagerImpl(eventCaller, headRepository);
        this.headManagerImpl.loadHeads();

        HologramProviderPicker hologramProviderPicker = new HologramProviderPicker();
        HologramProvider hologramProvider = hologramProviderPicker.pickProvider(this);

        HologramService hologramService = new HologramService(
            config,
            miniMessage,
            server,
            this.headManagerImpl,
            hologramProvider
        );
        hologramService.loadHolograms();

        BlockService blockService = new BlockService(config, notificationAnnouncer, this.headManagerImpl);

        ReloadService reloadService = new ReloadService()
            .register(configurationService)
            .register(hologramService);

        this.getCommand("heads")
            .setExecutor(new HeadCommand(config, notificationAnnouncer, blockService, reloadService));

        Stream.of(
            new HeadController(config, this.headManagerImpl, notificationAnnouncer),

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
        if (this.headManagerImpl != null) {
            this.headManagerImpl.clearHeads();
        }

        if (this.skullAPI != null) {
            this.skullAPI.shutdown();
        }

        if (this.audienceProvider != null) {
            this.audienceProvider.close();
        }
    }

    @Override
    public HeadManager getHeadManager() {
        return this.headManagerImpl;
    }
}
