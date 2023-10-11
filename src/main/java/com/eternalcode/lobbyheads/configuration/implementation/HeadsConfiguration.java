package com.eternalcode.lobbyheads.configuration.implementation;

import com.eternalcode.lobbyheads.delay.DelaySettings;
import com.eternalcode.lobbyheads.head.Head;
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
    public Duration headReplacementDelay = Duration.ofSeconds(15);

    @Comment({ " ", "# Heads list, don't touch this!" })
    public List<Head> heads = new ArrayList<>();

    @Comment({ " ", "# Messages configuration, you can change messages here" })
    public Messages messages = new Messages();

    @Comment({ " ", "# Head configuration, you can change head settings here" })
    public HeadSection headSection = new HeadSection();

    @Override
    public Duration delay() {
        return this.headReplacementDelay;
    }

    public static class HeadSection extends OkaeriConfig {
        @Comment("# Format of the head, you can use PlaceholderAPI here")
        public String defaultHeadFormat = "<gradient:#8dc63f:#53a30e>{PLAYER}</gradient>";
        public String headFormat = "<gradient:#8dc63f:#53a30e>%luckperms_prefix% {PLAYER}</gradient>";

        @Comment({ " ", "# Sound when a player replaces head" })
        public boolean soundEnabled = true;
        public Sound sound = Sound.ENTITY_PLAYER_LEVELUP;
        public int volume = 1;
        public int pitch = 1;

        @Comment("# Particle when a player replaces head")
        public boolean particleEnabled = true;
        public Particle particle = Particle.VILLAGER_HAPPY;
        public int count = 10;
    }

    public static class Messages extends OkaeriConfig {
        @Comment("# Message when usage is invalid")
        public String commandInvalidUsage = "<color:#ff3425>Hmmm, this doesn't look like a proper usage. Try: /head <add|remove|reload></color>";

        @Comment("# Reload configs")
        public String configurationReloaded = "<color:#8ceb34>LobbyHeads configuration reloaded!</color>";

        @Comment("# Message when a head already exists at the block intended for a new one")
        public String headAlreadyExists = "<color:#ff2d2d>Oops! It looks like this space is already occupied by another head.</color>";

        @Comment("# Message when a head has been added successfully")
        public String headAdded = "<color:#9ef442>Head added!</color>";

        @Comment("# Message when a head has been removed successfully")
        public String headRemoved = "<color:#93f542>Head removed!</color>";

        @Comment("# Message for commands that only players can execute")
        public String onlyForPlayers = "<color:#ff521d>Hey! This command is only for real players!</color>";

        @Comment("# Message when a player already replaced a head")
        public String playerAlreadyReplaceThisHead = "<color:#ff3f2a>Hey! It looks like you've already swapped this head.</color>";

        @Comment("# Message when a player must wait to replace another head")
        public String playerMustWaitToReplaceHead = "<color:#ff3f2a>Hey! You must wait <color:#ffea00>{duration}</color> seconds for next head replace.</color>";

        @Comment("# Message when a player is not looking at one")
        public String playerNotLookingAtHead = "<color:#ff3e24>Hey! You need to be looking at a head to do this.</color>";

        @Comment("# Message when a player is not permitted to replace heads")
        public String playerNotPermittedToReplaceHeads = "<color:#ff2f30>I'm sorry, but head swapping is only available for <b><color:#ffea00>VIP</color></b> members.</color>";

        @Comment("# Message when a player is not permitted to use an admin command")
        public String playerNotPermittedToUseThisCommand = "<color:#ff2528>You're not permitted to use this command!</color>";
    }
}
