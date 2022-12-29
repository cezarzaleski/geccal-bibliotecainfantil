package com.geccal.bibliotecainfantil

import com.geccal.bibliotecainfantil.infra.database.Connection
import com.geccal.bibliotecainfantil.infra.database.VertexConnectionAdapter
import org.flywaydb.core.Flyway
import org.junit.jupiter.api.BeforeAll
import org.testcontainers.containers.JdbcDatabaseContainer
import org.testcontainers.containers.MySQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.utility.DockerImageName

@Testcontainers
open class KtorIntegrationTest {

    companion object {
        @Container
        val container = mysql("mysql:latest") {
            withUsername("root")
            withPassword("test")
            withDatabaseName("test_biblioteca_infantil")
        }
        lateinit var connection: Connection

        @BeforeAll
        @JvmStatic
        fun beforeAll() {
            connection = VertexConnectionAdapter(
                anHost = container.host,
                anUser = container.username,
                aPassword = container.password,
                aPort = container.jdbcUrl.replace(Regex("[^-?0-9]+"), "").toInt(),
                aDatabaseName = container.databaseName
            )
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
