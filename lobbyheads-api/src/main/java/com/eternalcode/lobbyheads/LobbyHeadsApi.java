package com.eternalcode.lobbyheads;

import com.eternalcode.lobbyheads.head.HeadManager;
import org.jetbrains.annotations.NotNull;

/**
 * Root entry point for interacting with the LobbyHeads API.
 */
public interface LobbyHeadsApi {

    @NotNull HeadManager getHeadManager();
}
