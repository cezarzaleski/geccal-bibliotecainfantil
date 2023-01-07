package com.geccal.bibliotecainfantil

import com.geccal.bibliotecainfantil.infra.configuration.EnvironmentConfiguration
import com.geccal.bibliotecainfantil.infra.database.Connection
import com.geccal.bibliotecainfantil.infra.database.VertxConnectionAdapter

@Suppress("UtilityClassWithPublicConstructor")
open class IntegrationDatabaseTest {

    init {
        println("port")
        println(EnvironmentConfiguration.getInt("database.config.mysql.port"))
        println("host")
        println(EnvironmentConfiguration.getString("database.config.mysql.host"))
        println("username")
        println(EnvironmentConfiguration.getString("database.config.mysql.username"))
        println("password")
        println(EnvironmentConfiguration.getString("database.config.mysql.password"))
        println("database")
        println(EnvironmentConfiguration.getString("database.config.mysql.database"))

    }

    val port = EnvironmentConfiguration.getInt("database.config.mysql.port")
    val host = EnvironmentConfiguration.getString("database.config.mysql.host")
    val user = EnvironmentConfiguration.getString("database.config.mysql.username")
    val password = EnvironmentConfiguration.getString("database.config.mysql.password")
    val database = EnvironmentConfiguration.getString("database.config.mysql.database")

    val connection: Connection = VertxConnectionAdapter(
        aPort = port,
        anHost = host,
        anUser = user,
        aPassword = password,
        aDatabaseName = database
    )

    suspend fun cleanUp(tables: List<String>) {
        tables.forEach { table ->
            connection.persist("TRUNCATE $table", emptyMap())
        }
    }
}
