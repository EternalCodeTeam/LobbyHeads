package com.eternalcode.lobbyheads.head;


import com.eternalcode.lobbyheads.configuration.ConfigurationService;
import com.eternalcode.lobbyheads.configuration.implementation.HeadsConfiguration;

public class HeadRepositoryImpl implements HeadRepository {

    private final HeadsConfiguration config;
    private final ConfigurationService configurationService;

    public HeadRepositoryImpl(HeadsConfiguration config, ConfigurationService configurationService) {
        this.config = config;
        this.configurationService = configurationService;
    }

    @Override
    public void saveHead(Head head) {
        this.config.heads.add(head);
        this.configurationService.save(this.config);
    }

    @Override
    public void removeHead(Head head) {
        this.config.heads.remove(head);
        this.configurationService.save(this.config);
    }

    @Override
    public void updateHead(Head head) {
        int index = this.config.heads.indexOf(head);
        this.config.heads.set(index, head);
        this.configurationService.save(this.config);
    }
}
