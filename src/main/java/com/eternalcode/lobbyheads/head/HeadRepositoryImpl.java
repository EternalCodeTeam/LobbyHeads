package com.eternalcode.lobbyheads.head;


import com.eternalcode.lobbyheads.configuration.ConfigurationService;
import com.eternalcode.lobbyheads.configuration.implementation.HeadsConfiguration;

import java.util.concurrent.CompletableFuture;

public class HeadRepositoryImpl implements HeadRepository {

    private final HeadsConfiguration config;
    private final ConfigurationService configurationService;

    public HeadRepositoryImpl(HeadsConfiguration config, ConfigurationService configurationService) {
        this.config = config;
        this.configurationService = configurationService;
    }

    @Override
    public CompletableFuture<Void> saveHead(Head head) {
        this.config.heads.add(head);
        return CompletableFuture.runAsync(() -> this.configurationService.save(this.config));
    }

    @Override
    public CompletableFuture<Void> removeHead(Head head) {
        this.config.heads.remove(head);
        return CompletableFuture.runAsync(() -> this.configurationService.save(this.config));
    }

    @Override
    public CompletableFuture<Void> updateHead(Head head) {
        int index = this.config.heads.indexOf(head);
        this.config.heads.set(index, head);
        return CompletableFuture.runAsync(() -> this.configurationService.save(this.config));
    }
}
