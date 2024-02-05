package com.eternalcode.lobbyheads;

public class LobbyHeadsApiProvider {

    private static LobbyHeadsApi lobbyHeadsApi;

    public static LobbyHeadsApi provide() {
        if (lobbyHeadsApi == null) {
            throw new IllegalStateException("LobbyHeadsApi has not been initialized yet!");
        }

        return lobbyHeadsApi;
    }

    static void initialize(LobbyHeadsApi lobbyHeadsApi) {
        if (LobbyHeadsApiProvider.lobbyHeadsApi != null) {
            throw new IllegalStateException("LobbyHeadsApi has already been initialized!");
        }

        LobbyHeadsApiProvider.lobbyHeadsApi = lobbyHeadsApi;
    }

    static void deinitialize() {
        if (lobbyHeadsApi == null) {
            throw new IllegalStateException("LobbyHeadsApi has not been initialized yet!");
        }

        lobbyHeadsApi = null;
    }
}
