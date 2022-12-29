package com.geccal.bibliotecainfantil.infra.database
import io.vertx.kotlin.coroutines.await
import io.vertx.mysqlclient.MySQLConnectOptions
import io.vertx.mysqlclient.MySQLPool
import io.vertx.sqlclient.PoolOptions
import io.vertx.sqlclient.templates.SqlTemplate

class VertexConnectionAdapter(
    private val aPort: Int,
    private val anHost: String,
    private val anUser: String,
    private val aPassword: String,
    private val aDatabaseName: String,

) : Connection {
    private var client: MySQLPool
    init {
        val connectOptions = MySQLConnectOptions().apply {
            port = aPort
            host = anHost
            user = anUser
            password = aPassword
            database = aDatabaseName
        }
        val poolOptions = PoolOptions()
        client = MySQLPool.pool(connectOptions, poolOptions)
    }

    override suspend fun <T> query(statement: String, params: Map<String, Any>?): T {
            return SqlTemplate
                .forQuery(client, statement)
                .execute(params)
                .await() as T
    }

    override suspend fun <T> persist(statement: String, params: Map<String, Any?>?) {
        SqlTemplate
            .forUpdate(client, statement)
            .execute(params)
            .await()
    }
}
