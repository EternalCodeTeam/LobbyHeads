plugins {
    `checkstyle`
}

checkstyle {
    toolVersion = "10.20.1"

    configFile = file("${rootDir}/checkstyle/checkstyle.xml")
    configProperties["checkstyle.suppressions.file"] = "${rootDir}/checkstyle/suppressions.xml"

    maxErrors = 0
    maxWarnings = 0
}
