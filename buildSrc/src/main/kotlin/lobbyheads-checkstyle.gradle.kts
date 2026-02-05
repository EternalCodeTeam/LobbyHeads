plugins {
    `checkstyle`
}

checkstyle {
    toolVersion = "13.2.0"

    configFile = file("${rootDir}/checkstyle/checkstyle.xml")
    configProperties["checkstyle.suppressions.file"] = "${rootDir}/checkstyle/suppressions.xml"

    maxErrors = 0
    maxWarnings = 0
}
