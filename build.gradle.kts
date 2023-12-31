import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    `java-library`
    checkstyle

    id("net.minecrell.plugin-yml.bukkit") version "0.6.0"
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("xyz.jpenilla.run-paper") version "2.2.0"
}

group = "com.eternalcode"
version = "1.0.3"
description = "LobbyHeads"

repositories {
    gradlePluginPortal()
    mavenCentral()

    maven { url = uri("https://repo.panda-lang.org/releases") }
    maven { url = uri("https://hub.spigotmc.org/nexus/content/repositories/snapshots/") }
    maven { url = uri("https://jitpack.io") }
    maven { url = uri("https://repo.extendedclip.com/content/repositories/placeholderapi/") }
    maven { url = uri("https://repo.eternalcode.pl/releases") }
    maven { url = uri("https://storehouse.okaeri.eu/repository/maven-public/") }
    maven { url = uri("https://repository.minecodes.pl/releases") }
    maven { url = uri("https://libraries.minecraft.net/") }
    maven { url = uri("https://repo.papermc.io/repository/maven-public/") }

}

checkstyle {
    toolVersion = "10.12.4"

    configFile = file("${rootDir}/checkstyle/checkstyle.xml")
    configProperties["checkstyle.suppressions.file"] = "${rootDir}/checkstyle/suppressions.xml"

    maxErrors = 0
    maxWarnings = 0
}

dependencies {
    // okaeri configs
    val okaeriConfigsVersion = "5.0.0-beta.5"
    implementation("eu.okaeri:okaeri-configs-yaml-snakeyaml:${okaeriConfigsVersion}")
    implementation("eu.okaeri:okaeri-configs-serdes-commons:${okaeriConfigsVersion}")

    // a cool library, kyori
    implementation("net.kyori:adventure-platform-bukkit:4.3.1")
    implementation("net.kyori:adventure-text-minimessage:4.14.0")

    // Rollczi's skullapi
    implementation("dev.rollczi:liteskullapi:1.3.0")

    // spigot-api
    compileOnly("org.spigotmc:spigot-api:1.20.2-R0.1-SNAPSHOT")
    testImplementation("org.spigotmc:spigot-api:1.20.2-R0.1-SNAPSHOT")

    // mojang's authlib
    compileOnly("com.mojang:authlib:5.0.47")

    // HologramLib based on top of the protocolib
    implementation("com.github.unldenis:Hologram-Lib:2.6.0")

    // PlaceholderAPI, if anyone wants to parse placeholders in the head's name
    compileOnly("me.clip:placeholderapi:2.11.4")

    // bstats
    implementation("org.bstats:bstats-bukkit:3.0.2")

    // GitCheck
    implementation("com.eternalcode:gitcheck:1.0.0")

    // tests setup
    testImplementation("org.codehaus.groovy:groovy-all:3.0.19")
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.mockito:mockito-core:5.6.0")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

bukkit {
    main = "com.eternalcode.lobbyheads.HeadsPlugin"
    apiVersion = "1.13"
    prefix = "LobbyHeads"
    author = "EternalCodeTeam"
    name = "LobbyHeads"
    website = "www.eternalcode.pl"
    version = "${project.version}"
    depend = listOf("PlaceholderAPI", "ProtocolLib")

    commands {
        register("heads") {
            description = "Uses to manage lobbyheads."
            permission = "heads.admin"
            usage = "add|remove|reload"
        }
    }

}

tasks.compileJava {
    options.compilerArgs = listOf("-Xlint:deprecation", "-Xlint:unchecked")
    options.encoding = "UTF-8"
}

tasks.test {
    useJUnitPlatform()
}

tasks.runServer {
    minecraftVersion("1.20.1")

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

    dependsOn("checkstyleMain")
    dependsOn("checkstyleTest")
    dependsOn("test")

    mergeServiceFiles()

    val prefix = "com.eternalcode.lobbyheads.libs"
    listOf(
        "dev.rollczi",
        "eu.okaeri",
        "panda",
        "org.yaml",
        "net.kyori",
        "com.github.unldenis",
        "org.bstats",
        "com.eternalcode.gitcheck",
    ).forEach { relocate(it, prefix) }
}
