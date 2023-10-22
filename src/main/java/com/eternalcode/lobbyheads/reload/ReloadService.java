package com.eternalcode.lobbyheads.reload;

import com.eternalcode.lobbyheads.configuration.ConfigurationService;
import com.eternalcode.lobbyheads.head.hologram.HologramService;

public class ReloadService {

    private final HologramService hologramService;
    private final ConfigurationService configurationService;

    public ReloadService(HologramService hologramService, ConfigurationService configurationService) {
        this.hologramService = hologramService;
        this.configurationService = configurationService;
    }

    public void reload() {
        this.configurationService.reload();
        this.hologramService.updateHolograms();
    }
}
