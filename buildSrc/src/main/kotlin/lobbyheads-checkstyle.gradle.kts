plugins {
    `checkstyle`
}

checkstyle {
    toolVersion = "12.3.1"

    configFile = file("${rootDir}/checkstyle/checkstyle.xml")
    configProperties["checkstyle.suppressions.file"] = "${rootDir}/checkstyle/suppressions.xml"

    maxErrors = 0
    maxWarnings = 0
}
