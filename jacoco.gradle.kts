fun ignorePackagesInJacocoReport(classDirectories: ConfigurableFileCollection) {
    classDirectories.setFrom(files(classDirectories.files.map {
        fileTree(it).apply {
            exclude(
                "**/geccal/bibliotecainfantil/boundaries/api/plugins/**",
                )
        }
    }))
}

val minimumTestCoverage = 0.80.toBigDecimal()

val testReportFiles = listOf(
    file("$buildDir/jacoco/test.exec")
)

tasks.withType<JacocoReport> {
    executionData(testReportFiles)

    reports {
        xml.isEnabled = true
        csv.isEnabled = false
        html.isEnabled = true
        html.destination = file("$buildDir/reports/jacoco")
    }
    ignorePackagesInJacocoReport(classDirectories)
}

tasks.withType<JacocoCoverageVerification> {
    executionData(testReportFiles)
    violationRules { rule { limit { minimum = minimumTestCoverage } } }
    ignorePackagesInJacocoReport(classDirectories)
}