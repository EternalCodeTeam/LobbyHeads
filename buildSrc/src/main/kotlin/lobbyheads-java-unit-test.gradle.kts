plugins {
    `java-library`
}

dependencies {
    testImplementation("org.codehaus.groovy:groovy-all:3.0.21")
    testImplementation(platform("org.junit:junit-bom:5.11.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testImplementation("org.mockito:mockito-core:5.12.0")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}

sourceSets.test {
    java.setSrcDirs(listOf("test"))
    resources.setSrcDirs(emptyList<String>())
}
