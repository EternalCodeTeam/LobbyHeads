package com.eternalcode.lobbyheads.reload;

import java.util.HashSet;
import java.util.Set;

public class ReloadService {

    private final Set<Reloadable> reloadables = new HashSet<>();

    public ReloadService register(Reloadable reloadable) {
        this.reloadables.add(reloadable);
        return this;
    }

    public void reload() {
        for (Reloadable reloadable : this.reloadables) {
            reloadable.reload();
        }
    }
}
