package com.geccal.bibliotecainfantil


import org.flywaydb.core.Flyway
import org.flywaydb.core.api.configuration.ClassicConfiguration
import org.junit.jupiter.api.BeforeAll
import org.testcontainers.containers.JdbcDatabaseContainer
import org.testcontainers.containers.MySQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.utility.DockerImageName
import kotlin.properties.Delegates

@Testcontainers
open class KtorIntegrationTest {

    companion object {
        @Container
        val container = mysql("mysql:latest") {
            withUsername("root")
            withPassword("test")
            withDatabaseName("test_biblioteca_infantil")
        }
        var username: String = container.username
        var password: String = container.password
        var dabaseName: String = container.databaseName
        lateinit var host: String
        var port by Delegates.notNull<Int>()

        @BeforeAll
        @JvmStatic
        fun beforeAll() {
            host = container.host
            port = container.jdbcUrl.replace(Regex("[^-?0-9]+"), "").toInt()
            migrateFlyway()
        }

        private fun migrateFlyway() {
            val migration = Flyway.configure()
                .dataSource(
                    "${container.jdbcUrl}",
                    container.username,
                    container.password
                ).load()
            migration.migrate()
        }
    }
}

fun mysql(imageName: String, configs: JdbcDatabaseContainer<Nothing>.() -> Unit) =
    MySQLContainer<Nothing>(
        DockerImageName.parse(imageName).asCompatibleSubstituteFor("mysql")
    ).apply(configs)
