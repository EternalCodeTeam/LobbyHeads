package com.eternalcode.lobbyheads.configuration.implementation;

import com.eternalcode.lobbyheads.delay.DelaySettings;
import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import eu.okaeri.configs.annotation.Header;
import eu.okaeri.configs.annotation.NameModifier;
import eu.okaeri.configs.annotation.NameStrategy;
import eu.okaeri.configs.annotation.Names;
import org.bukkit.Particle;
import org.bukkit.Sound;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Names(strategy = NameStrategy.HYPHEN_CASE, modifier = NameModifier.TO_LOWER_CASE)
@Header("# ")
@Header("# LobbyHeads configuration file")
@Header("# Permissions:")
@Header("# - lobbyheads.replace - allows replacing heads")
@Header("# - lobbyheads.admin - allows adding/removing head")
@Header("# ")
public class HeadsConfiguration extends OkaeriConfig implements DelaySettings {

    @Comment("# Delay between replacing heads")
    public Duration delay = Duration.ofSeconds(15);

    @Comment({ " ", "# Heads list, don't touch this!" })
    public List<com.eternalcode.lobbyheads.head.Head> heads = new ArrayList<>();

    @Comment({ " ", "# Messages configuration, you can change messages here" })
    public Messages messages = new Messages();

    @Comment({ " ", "# Head configuration, you can change head settings here" })
    public Head head = new Head();

    @Override
    public Duration delay() {
        return this.delay;
    }

    public static class Head extends OkaeriConfig {
        @Comment("# Format of the head, you can use PlaceholderAPI here")
        public String defaultHeadFormat = "<gradient:#8dc63f:#53a30e>{PLAYER}</gradient>";
        public String headFormat = "<gradient:#8dc63f:#53a30e>%luckperms_prefix% {PLAYER}</gradient>";

        @Comment({ " ", "# Sound when a player replaces head" })
        public boolean soundEnabled = true;
        public Sound sound = Sound.ENTITY_PLAYER_LEVELUP;
        public int volume = 1;
        public int pitch = 1;

        @Comment("# Particle when player replaces head")
        public boolean particleEnabled = true;
        public Particle particle = Particle.VILLAGER_HAPPY;
        public int count = 10;
    }

    public static class Messages extends OkaeriConfig {
        @Comment("# Message when usage is invalid")
        public String invalidUsage = "<color:#ff3425>Hmmm, this doesn't look like a proper usage. Try: /head <add|remove|reload></color>";

        @Comment("# Reload configs")
        public String configurationReloaded = "<color:#8ceb34>LobbyHeads configuration reloaded!</color>";

        @Comment("# Message for commands that only players can execute")
        public String onlyForPlayers = "<color:#ff521d>Hey! This command is only for real players!</color>";

        @Comment("# Message when a head has been added successfully")
        public String headAdded = "<color:#9ef442>Head added!</color>";

        @Comment("# Message when a head has been removed successfully")
        public String headRemoved = "<color:#93f542>Head removed!</color>";

        @Comment("# Message when a head already exists at the block intended for a new one")
        public String headAlreadyExists = "<color:#ff2d2d>Oops! It looks like this space is already occupied by another head.</color>";

        @Comment("# Message when a head is expected, but the player is not looking at one")
        public String youAreNotLookingAtHead = "<color:#ff3e24>Hey! You need to be looking at a head to do this.</color>";

        @Comment("# Message when a player is not permitted to replace heads")
        public String youAreNotPermittedToReplaceHeads = "<color:#ff2f30>I'm sorry, but head swapping is only available for <b><color:#ffea00>VIP</color></b> members.</color>";

        @Comment("# Message when a player is not permitted to use an admin command")
        public String youAreNotPermittedToUseThisCommand = "<color:#ff2528>You're not permitted to use this command!</color>";

        @Comment("# Message when a player already replaced a head")
        public String youAreAlreadyReplaceThisHead = "<color:#ff3f2a>Hey! It looks like you've already swapped this head.</color>";
    }
}
