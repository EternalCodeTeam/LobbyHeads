package com.eternalcode.lobbyheads;

import org.jetbrains.annotations.NotNull;

/**
 * Provides global access to the LobbyHeads API.
 */
public final class LobbyHeadsApiProvider {

    private static volatile LobbyHeadsApi api;

    private LobbyHeadsApiProvider() {
        throw new UnsupportedOperationException("This is a utility class.");
    }

    /**
     * @return The initialized LobbyHeadsApi instance.
     * @throws IllegalStateException if not initialized.
     */
    public static @NotNull LobbyHeadsApi get() {
        LobbyHeadsApi instance = api;
        if (instance == null) {
            throw new IllegalStateException("LobbyHeadsApi is not initialized yet.");
        }
        return instance;
    }

    /**
     * Internal use only.
     */
    static void initialize(@NotNull LobbyHeadsApi lobbyHeadsApi) {
        if (api != null) {
            throw new IllegalStateException("LobbyHeadsApi is already initialized.");
        }
        api = lobbyHeadsApi;
    }

    static void deinitialize() {
        if (api == null) {
            throw new IllegalStateException("LobbyHeadsApi is not initialized.");
        }
        api = null;
    }
}
