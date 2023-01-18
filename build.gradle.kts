val ktor_version: String by project
val kotlin_version: String by project
val logback_version: String by project
val assertj_version: String by project
val detekt_version: String by project
val junit_version: String by project
val mockk_version: String by project
val vertex_mysql_version: String by project
val flyway_version: String by project
val testcontainers_version: String by project
val mysql_version: String by project
val flyway_mysql_version: String by project
val jackson_datatype_jsr: String by project

plugins {
    application
    kotlin("jvm") version "1.7.22"
    id("jacoco")
    id("io.ktor.plugin") version "2.1.3"
    id("io.gitlab.arturbosch.detekt") version "1.22.0"
}

sourceSets {
    create("componentTest") {
        compileClasspath += sourceSets.main.get().output
        runtimeClasspath += sourceSets.main.get().output
    }
}

group = "com.geccal"
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

    // Database
    implementation("org.flywaydb:flyway-core:$flyway_version")
    implementation("org.flywaydb:flyway-mysql:$flyway_mysql_version")
    implementation("io.vertx:vertx-mysql-client:$vertex_mysql_version")
    implementation("io.vertx:vertx-lang-kotlin:$vertex_mysql_version")
    implementation("io.vertx:vertx-lang-kotlin-coroutines:$vertex_mysql_version")
    implementation("io.vertx:vertx-sql-client-templates:$vertex_mysql_version")
    implementation("mysql:mysql-connector-java:$mysql_version")
    implementation("io.ktor:ktor-server-cors-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-auto-head-response-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-call-logging-jvm:$ktor_version")
    implementation("io.ktor:ktor-client-json-jvm:$ktor_version")
    implementation("io.ktor:ktor-client-jackson:$ktor_version")
    implementation("io.ktor:ktor-server-content-negotiation:$ktor_version")
    implementation("io.ktor:ktor-serialization-jackson:$ktor_version")
    implementation("io.ktor:ktor-server-host-common-jvm:$ktor_version")
    implementation("io.ktor:ktor-server-status-pages-jvm:$ktor_version")
    implementation("com.fasterxml.jackson.datatype:$jackson_datatype_jsr")

    detekt("io.gitlab.arturbosch.detekt:detekt-cli:$detekt_version")
    detekt("io.gitlab.arturbosch.detekt:detekt-formatting:$detekt_version")

    testImplementation("io.ktor:ktor-server-tests-jvm:$ktor_version")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit:$kotlin_version")
    testImplementation("org.assertj:assertj-core:$assertj_version")
    testImplementation("org.junit.jupiter:junit-jupiter-api:$junit_version")
    testImplementation("org.junit.jupiter:junit-jupiter-engine:$junit_version")
    testImplementation("org.junit.jupiter:junit-jupiter-params:$junit_version")
    testImplementation("io.mockk:mockk:$mockk_version")
    testImplementation("org.testcontainers:mysql:$testcontainers_version")
    testImplementation("org.testcontainers:junit-jupiter:$testcontainers_version")
    testImplementation("net.java.dev.jna:jna-platform"){
        version {
            strictly("[5.7,5.10]")
        }
    }

    val componentTest = task<Test>("componentTest") {
        description = "Runs integration tests"
        group = "verification"

        testClassesDirs = sourceSets["componentTest"].output.classesDirs
        classpath = sourceSets["componentTest"].runtimeClasspath
        shouldRunAfter("test")
    }

    tasks.check { dependsOn(componentTest) }

    val componentTestImplementation: Configuration by configurations.getting {
        extendsFrom(configurations.testImplementation.get())
    }

    configurations["componentTestRuntimeOnly"].extendsFrom(configurations.runtimeOnly.get())


    componentTestImplementation(sourceSets.test.get().output)


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
