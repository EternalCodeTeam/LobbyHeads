package com.eternalcode.lobbyheads.head;

import com.eternalcode.lobbyheads.configuration.ConfigurationService;
import com.eternalcode.lobbyheads.configuration.implementation.HeadsConfiguration;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CopyOnWriteArrayList;

public class HeadRepositoryImpl implements HeadRepository {

    private final HeadsConfiguration config;
    private final ConfigurationService configurationService;

    public HeadRepositoryImpl(HeadsConfiguration config, ConfigurationService configurationService) {
        this.config = config;
        this.configurationService = configurationService;

        if (!(this.config.heads instanceof CopyOnWriteArrayList)) {
            this.config.heads = new CopyOnWriteArrayList<>(this.config.heads);
        }
    }

    @Override
    public CompletableFuture<Void> saveHead(Head head) {
        return CompletableFuture.runAsync(() -> {
            this.config.heads.add(head);
            this.configurationService.save(this.config);
        });
    }

    @Override
    public CompletableFuture<Void> removeHead(Head head) {
        return CompletableFuture.runAsync(() -> {
            this.config.heads.remove(head);
            this.configurationService.save(this.config);
        });
    }

    @Override
    public CompletableFuture<Void> updateHead(Head head) {
        return CompletableFuture.runAsync(() -> {
            int index = this.config.heads.indexOf(head);

            if (index == -1) {
                this.config.heads.add(head);
            }
            else {
                this.config.heads.set(index, head);
            }

            this.configurationService.save(this.config);
        });
    }

    @Override
    public CompletableFuture<List<Head>> loadHeads() {
        return CompletableFuture.completedFuture(new ArrayList<>(this.config.heads));
    }
}
