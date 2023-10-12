<div align="center">

![LobbyHeads](/assets/lobbyheads-banner.png)

[![Supported by Paper](https://raw.githubusercontent.com/intergrav/devins-badges/v3/assets/cozy/supported/paper_vector.svg)](https://papermc.io)
[![Supported by Spigot](https://raw.githubusercontent.com/intergrav/devins-badges/v3/assets/cozy/supported/spigot_vector.svg)](https://spigotmc.org)
[![Patreon](https://raw.githubusercontent.com/intergrav/devins-badges/v3/assets/cozy/donate/patreon-plural_vector.svg)](https://www.patreon.com/eternalcode)
[![Official Website](https://raw.githubusercontent.com/intergrav/devins-badges/v3/assets/cozy/documentation/website_vector.svg)](https://eternalcode.pl/)
[![Join Our Discord](https://raw.githubusercontent.com/intergrav/devins-badges/v3/assets/cozy/social/discord-plural_vector.svg)](https://discord.gg/FQ7jmGBd6c)
[![Built with Gradle](https://raw.githubusercontent.com/intergrav/devins-badges/v3/assets/cozy/built-with/gradle_vector.svg)](https://gradle.org/)
[![Built with Java](https://raw.githubusercontent.com/intergrav/devins-badges/v3/assets/cozy/built-with/java17_vector.svg)](https://www.java.com/)
</div>

## About LobbyHeads

LobbyHeads is a Minecraft server hub plugin, designed to adorn your hub spaces with decorative heads. Designed to be highly configurable, it allows designated players to replace these decorative heads with their own, displaying a holographic nickname and skin for personalized flare. This adds an extra layer of cosmetic reward for patrons and special rank holders.

Built to be compatible and extensively tested with Minecraft versions 1.17.1 through 1.20.2, LobbyHeads should function seamlessly with other Minecraft versions too. Always ensure your server is using Java 17 or later for the smoothest experience.

If you find any bugs, issues or inconsistencies, kindly report them [here](https://github.com/eternalcodeteam/lobbyheads/issues).

## Plugin Preview

_TODO: Add a GIF here._

## Permission Assignments

LobbyHeads offers two permissions for administrators to assign:

| Permission           | Description                                                     |
|:---------------------|:----------------------------------------------------------------|
| `lobbyheads.replace` | Allows the user to replace decorative heads whenever they want. |
| `lobbyheads.admin`   | Grants the user permission to add or remove decorative heads.   |

## Usage Guidelines

Follow the steps below to use LobbyHeads:

1. Acquire a player head via game mode and place.
2. Looking at placed head, type `/heads add` to place the player head.
3. If you wish to remove a head, stand in front of it and type `/heads remove`.
4. Well done! You have successfully interacted with LobbyHeads.

## For developers

### Build Process

Use the command `./gradlew shadowJar` to build the project.

The output file `LobbyHeads-<version>.jar` will be located in the `build/libs` directory.

### Contributions

We wholeheartedly welcome your contributions to LobbyHeads! Please refer to our [contribution guidelines](.github/CONTRIBUTING.md) for detailed procedures on how you can contribute, and our [code of conduct](./.github/CODE_OF_CONDUCT.md) to ensure a harmonious and welcoming community.

### Issue Reporting

Experiencing an issue with the plugin? Report it under our [Issues tab](https://github.com/EternalCodeTeam/LobbyHeads/issues). Be sure to provide maximum information, such as your Minecraft version, plugin version, and relevant error messages or logs for an efficient diagnosis.
