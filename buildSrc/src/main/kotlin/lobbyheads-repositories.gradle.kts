plugins {
    id("java-library")
}

repositories {
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
