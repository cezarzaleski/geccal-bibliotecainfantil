val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project
val assertj_version: String by project
val detekt_version: String by project
val junit_version: String by project
val mockk_version: String by project

plugins {
    application
    kotlin("jvm") version "1.7.22"
    id("jacoco")
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
    testImplementation("org.junit.jupiter:junit-jupiter-api:$junit_version")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:$junit_version")
    testImplementation("org.junit.jupiter:junit-jupiter-params:$junit_version")
    testImplementation("io.mockk:mockk:$mockk_version")

}

detekt {
    autoCorrect = true
    toolVersion = detekt_version
    input = files("src")
    parallel = true
    config = files("./detekt-config.yml")
    allRules = true
}

tasks.withType<Test> {
    jvmArgs = listOf("-Duser.timezone=America/Sao_Paulo")
    useJUnitPlatform()
}


tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_17.toString()
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
    jvmArgs(listOf("--add-opens=java.base/java.time=ALL-UNNAMED"))
}

subprojects {
    apply(from = "$rootDir/gradle/scripts/jacoco.gradle.kts")
}
apply(from = "jacoco.gradle.kts")
