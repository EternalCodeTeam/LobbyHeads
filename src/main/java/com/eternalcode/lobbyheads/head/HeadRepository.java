package com.eternalcode.lobbyheads.head;

import java.util.concurrent.CompletableFuture;

public interface HeadRepository {

    CompletableFuture<Void> saveHead(Head head);

    CompletableFuture<Void> removeHead(Head head);

    CompletableFuture<Void> updateHead(Head head);

}
