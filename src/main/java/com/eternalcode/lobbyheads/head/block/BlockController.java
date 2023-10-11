package com.eternalcode.lobbyheads.head.block;

import com.eternalcode.lobbyheads.head.event.HeadCreateEvent;
import com.eternalcode.lobbyheads.position.Position;
import com.eternalcode.lobbyheads.position.PositionAdapter;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import dev.rollczi.liteskullapi.SkullAPI;
import dev.rollczi.liteskullapi.SkullData;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.Skull;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.lang.reflect.Field;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class BlockController implements Listener {

    private static final String SKULL_TEXTURE_PROPERTY_KEY = "textures";

    private final Plugin plugin;
    private final BukkitScheduler scheduler;
    private final SkullAPI skullAPI;

    public BlockController(Plugin plugin, BukkitScheduler scheduler, SkullAPI skullAPI) {
        this.plugin = plugin;
        this.scheduler = scheduler;
        this.skullAPI = skullAPI;
    }

    @EventHandler
    private void block(HeadCreateEvent event) {
        Position position = event.getPosition();
        Location convert = PositionAdapter.convert(position);
        Block block = convert.getBlock();

        if (!(block.getState() instanceof Skull skull)) {
            return;
        }

        SkullData skullData = this.skullAPI.awaitSkullData(event.getUuid(), 5, TimeUnit.SECONDS);
        this.prepareSkullUpdate(this.scheduler, skullData, skull);
    }

    private void prepareSkullUpdate(BukkitScheduler scheduler, SkullData skullData, Skull skull) {
        scheduler.runTask(this.plugin, () -> this.updateSkull(skullData, skull));
    }

    private void updateSkull(SkullData skullData, Skull skull) {
        GameProfile gameProfile = new GameProfile(UUID.randomUUID(), null);
        gameProfile.getProperties().put(SKULL_TEXTURE_PROPERTY_KEY, new Property(SKULL_TEXTURE_PROPERTY_KEY, skullData.getValue()));

        try {
            Field profileField = skull.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(skull, gameProfile);
        }
        catch (NoSuchFieldException | IllegalAccessException exception) {
            exception.printStackTrace();
        }

        skull.update();
    }
}
