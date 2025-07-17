<div align="center">

![LobbyHeads](/assets/lobbyheads-banner.png)

[![Available on SpigotMC](https://raw.githubusercontent.com/vLuckyyy/badges/main/available-on-spigotmc.svg)](https://www.spigotmc.org/resources/lobbyheads-%E2%9C%A8-decorative-heads-for-your-server-hub.113065/)
[![Available on Modrinth](https://raw.githubusercontent.com/vLuckyyy/badges/main/avaiable-on-modrinth.svg)](https://modrinth.com/plugin/LobbyHeads)
[![Available on Hangar](https://raw.githubusercontent.com/vLuckyyy/badges/main/avaiable-on-hangar.svg)](https://hangar.papermc.io/EternalCodeTeam/LobbyHeads)

[![Chat on Discord](https://raw.githubusercontent.com/vLuckyyy/badges/main//chat-with-us-on-discord.svg)](https://discord.com/invite/FQ7jmGBd6c)
[![Read the Docs](https://raw.githubusercontent.com/vLuckyyy/badges/main/read-the-documentation.svg)](https://docs.eternalcode.pl/eternalcore/introduction)
[![Available on BStats](https://raw.githubusercontent.com/vLuckyyy/badges/main/available-on-bstats.svg)](https://bstats.org/plugin/bukkit/LobbyHeads/20048)
</div>

## About LobbyHeads

LobbyHeads is a Minecraft server hub plugin, designed to adorn your hub spaces with decorative heads. Designed to be highly configurable, it allows designated players to replace these decorative heads with their own, displaying a holographic nickname and skin for personalized flare. This adds an extra layer of cosmetic reward for patrons and special rank holders.

Built to be compatible and extensively tested with Minecraft versions 1.17.1 through 1.20.2, LobbyHeads should function seamlessly with other Minecraft versions too. Always ensure your server is using Java 17 or later for the smoothest experience.

If you find any bugs, issues or inconsistencies, kindly report them [here](https://github.com/eternalcodeteam/lobbyheads/issues).

## Plugin Preview

### How head work? (You can use this example for **premium** ranks on your server!)
![gif](https://github.com/EternalCodeTeam/LobbyHeads/blob/master/assets/head%20work%2020fps.gif?raw=true)


### How to set up heads? (`/heads add` comand)
![gif](https://github.com/EternalCodeTeam/LobbyHeads/blob/master/assets/head%20setup%2020fps.gif?raw=true)

## Permission Assignments

LobbyHeads offers two permissions for administrators to assign:

| Permission                  | Description                                                                     |
|:----------------------------|:--------------------------------------------------------------------------------|
| `lobbyheads.replace`        | Allows the user to replace decorative heads whenever they want.                 |
| `lobbyheads.admin`          | Grants the user permission to add or remove decorative heads.                   |
| `lobbyheads.receiveupdates` | Grants the user permission to receive updates about new versions of the plugin. |

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

We wholeheartedly welcome your contributions to LobbyHeads! Please refer to our [contribution guidelines](https://github.com/EternalCodeTeam/LobbyHeads/blob/master/.github/CONTRIBUTING.md) for detailed procedures on how you can contribute, and our [code of conduct](./.github/CODE_OF_CONDUCT.md) to ensure a harmonious and welcoming community.

### Issue Reporting

Experiencing an issue with the plugin? Report it under our [Issues tab](https://github.com/EternalCodeTeam/LobbyHeads/issues). Be sure to provide maximum information, such as your Minecraft version, plugin version, and relevant error messages or logs for an efficient diagnosis.
