package com.geccal.bibliotecainfantil.infra.repository

import com.geccal.bibliotecainfantil.core.domain.pagination.SearchQuery
import com.geccal.bibliotecainfantil.infra.database.Connection
import io.vertx.sqlclient.Row
import io.vertx.sqlclient.RowSet

open class AbstractVertexRepository(
    private val connection: Connection
) {
    protected fun String.mountPaginated(query: SearchQuery): String = this + """ ORDER BY ${query.sort}
        | ${query.direction} LIMIT ${query.perPage} OFFSET ${query.page}""".trimMargin()

    protected fun RowSet<Row>.isEmpty() = this.size() == 0

    protected suspend fun countItems(statement: String, params: Map<String, Any>): Long {
        val statementCount = "SELECT count(1) as total from ($statement) as query"
        return connection.query<RowSet<Row>>(statementCount, params)
            .first().getLong("total")
    }
}
