plugins {
    id("java-library")
}

group = "com.eternalcode"
version = "1.0.4-SNAPSHOT"

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}

tasks.compileJava {
    options.compilerArgs = listOf("-Xlint:deprecation", "-parameters")
    options.encoding = "UTF-8"
    options.release = 21
}
