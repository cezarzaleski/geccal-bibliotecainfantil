val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project
val assertj_version: String by project
val detekt_version: String by project

plugins {
    application
    kotlin("jvm") version "1.7.22"
    id("io.ktor.plugin") version "2.1.3"
    id("io.gitlab.arturbosch.detekt") version "1.22.0"
}

group = "com.example"
version = "0.0.1"
application {
    mainClass.set("com.geccal.bibliotecainfantil.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    implementation("io.ktor:ktor-server-core-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-netty-jvm:$ktor_version")
    implementation("ch.qos.logback:logback-classic:$logback_version")
    detekt("io.gitlab.arturbosch.detekt:detekt-cli:$detekt_version")
    detekt("io.gitlab.arturbosch.detekt:detekt-formatting:$detekt_version")

    testImplementation("io.ktor:ktor-server-tests-jvm:$ktor_version")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
    testImplementation("org.assertj:assertj-core:$assertj_version")

}

detekt {
    autoCorrect = true
    toolVersion = detekt_version
    input = files("src")
    parallel = true
    config = files("./detekt-config.yml")
    allRules = true
}
