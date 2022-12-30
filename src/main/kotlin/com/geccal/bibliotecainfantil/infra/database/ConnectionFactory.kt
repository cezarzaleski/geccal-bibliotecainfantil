package com.geccal.bibliotecainfantil.infra.database

import com.geccal.bibliotecainfantil.infra.configuration.Environment

object ConnectionFactory {

    private val port = Environment.getString("database.config.mysql.port").toInt()
    private val host = Environment.getString("database.config.mysql.host")
    private val user = Environment.getString("database.config.mysql.username")
    private val password = Environment.getString("database.config.mysql.password")
    private val database = Environment.getString("database.config.mysql.database")

    val vertxConnectionAdapter: Connection = VertxConnectionAdapter(
        aPort = port,
        anHost = host,
        anUser = user,
        aPassword = password,
        aDatabaseName = database
    )
}
