import net.minecrell.pluginyml.bukkit.BukkitPluginDescription
import net.minecrell.pluginyml.paper.PaperPluginDescription

plugins {
    `lobbyheads-java-21`
    `lobbyheads-repositories`
    `lobbyheads-checkstyle`

    id("de.eldoria.plugin-yml.bukkit") version "0.7.1"
    id("com.gradleup.shadow") version "8.3.5"
    id("xyz.jpenilla.run-paper") version "2.3.1"
}


val mainPackage = "com.eternalcode.lobbyheads"
val projectPrefix = "LobbyHeads"

dependencies {
    // okaeri configs
    val okaeriConfigsVersion = "5.0.9"
    implementation("eu.okaeri:okaeri-configs-yaml-snakeyaml:${okaeriConfigsVersion}")
    implementation("eu.okaeri:okaeri-configs-serdes-commons:${okaeriConfigsVersion}")

    // api
    api(project(":lobbyheads-api"))

    // Rollczi's skullapi
    implementation("dev.rollczi:liteskullapi:1.3.0")

    // eternalcode commons
    val eternalCodeCommonsVersion = "1.1.7"
    implementation("com.eternalcode:eternalcode-commons-adventure:$eternalCodeCommonsVersion")
    implementation("com.eternalcode:eternalcode-commons-shared:$eternalCodeCommonsVersion")
    implementation("com.eternalcode:eternalcode-commons-bukkit:$eternalCodeCommonsVersion")

    compileOnly("io.papermc.paper:paper-api:1.21.7-R0.1-SNAPSHOT")

    implementation("com.github.cryptomorin:XSeries:12.1.0")

    compileOnly("de.oliver:FancyHolograms:2.4.0")
    compileOnly("com.github.decentsoftware-eu:decentholograms:2.9.5")

    // PlaceholderAPI, if anyone wants to parse placeholders in the head's name
    compileOnly("me.clip:placeholderapi:2.11.6")

    // bstats
    implementation("org.bstats:bstats-bukkit:3.1.0")

    // GitCheck
    implementation("com.eternalcode:gitcheck:1.0.0")
}

bukkit {
    name = projectPrefix
    version = "${project.version}"
    author = "EternalCoreTeam"
    website = "https://eternalcode.pl/"
    prefix = projectPrefix

    main = "$mainPackage.HeadsPlugin"
    apiVersion = "1.13"

    depend = listOf("PlaceholderAPI")
    softDepend = listOf("DecentHolograms", "FancyHolograms")

    commands {
        register("heads") {
            description = "Uses to manage lobbyheads."
            permission = "heads.admin"
            usage = "add|remove|reload"
        }
    }
}

tasks.runServer {
    minecraftVersion("1.21.7")

    downloadPlugins {
        hangar("ProtocolLib", "5.1.0")
        hangar("PlaceholderAPI", "2.11.5")
    }
}

tasks.shadowJar {
    archiveFileName.set("LobbyHeads v${project.version}.jar")

    exclude(
        "org/intellij/lang/annotations/**",
        "org/jetbrains/annotations/**",
        "META-INF/**",
    )


    mergeServiceFiles()

    val prefix = "com.eternalcode.lobbyheads.libs"
    listOf(
        "dev.rollczi",
        "eu.okaeri",
        "panda",
        "org.yaml",
        "org.bstats",
        "com.eternalcode.gitcheck",
        "com.eternalcode.commons",
    ).forEach { relocate(it, prefix) }
}
