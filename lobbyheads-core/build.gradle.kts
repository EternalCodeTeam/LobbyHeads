plugins {
    `lobbyheads-java-17`
    `lobbyheads-repositories`
    `lobbyheads-java-unit-test`
    `lobbyheads-checkstyle`

    id("net.minecrell.plugin-yml.bukkit") version "0.6.0"
    id("com.gradleup.shadow") version "8.3.5"
    id("xyz.jpenilla.run-paper") version "2.3.1"
}

dependencies {
    // okaeri configs
    val okaeriConfigsVersion = "5.0.5"
    implementation("eu.okaeri:okaeri-configs-yaml-snakeyaml:${okaeriConfigsVersion}")
    implementation("eu.okaeri:okaeri-configs-serdes-commons:${okaeriConfigsVersion}")

    // api
    api(project(":lobbyheads-api"))

    // a cool library, kyori
    implementation("net.kyori:adventure-platform-bukkit:4.3.4")
    implementation("net.kyori:adventure-text-minimessage:4.17.0")

    // Rollczi's skullapi
    implementation("dev.rollczi:liteskullapi:1.3.0")

    // eternalcode commons
    implementation("com.eternalcode:eternalcode-commons-adventure:1.1.7")

    // spigot-api
    compileOnly("org.spigotmc:spigot-api:1.20.2-R0.1-SNAPSHOT")
    testImplementation("org.spigotmc:spigot-api:1.20.2-R0.1-SNAPSHOT")

    // mojang's authlib
    compileOnly("com.mojang:authlib:5.0.47")

    // HoloEasy based on top of the protocolib
    implementation("com.github.unldenis:holoeasy:3.0.1")

    // PlaceholderAPI, if anyone wants to parse placeholders in the head's name
    compileOnly("me.clip:placeholderapi:2.11.7")

    // bstats
    implementation("org.bstats:bstats-bukkit:3.1.0")

    // GitCheck
    implementation("com.eternalcode:gitcheck:1.0.0")

    // tests setup
    testImplementation("org.codehaus.groovy:groovy-all:3.0.25")
    testImplementation(platform("org.junit:junit-bom:5.11.4"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.mockito:mockito-core:5.14.2")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
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
    options.compilerArgs = listOf("-Xlint:deprecation", "-parameters")
    options.encoding = "UTF-8"
    options.release = 17
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
        "com.unldenis",
        "org.bstats",
        "com.eternalcode.gitcheck",
        "com.eternalcode.commons",
    ).forEach { relocate(it, prefix) }
}
