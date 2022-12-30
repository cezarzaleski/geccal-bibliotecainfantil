package com.geccal.bibliotecainfantil.infra.database

import com.geccal.bibliotecainfantil.infra.configuration.EnvironmentConfiguration

object ConnectionFactory {

    private val port = EnvironmentConfiguration.getString("database.config.mysql.port").toInt()
    private val host = EnvironmentConfiguration.getString("database.config.mysql.host")
    private val user = EnvironmentConfiguration.getString("database.config.mysql.username")
    private val password = EnvironmentConfiguration.getString("database.config.mysql.password")
    private val database = EnvironmentConfiguration.getString("database.config.mysql.database")

    val vertxConnectionAdapter: Connection = VertxConnectionAdapter(
        aPort = port,
        anHost = host,
        anUser = user,
        aPassword = password,
        aDatabaseName = database
    )
}
