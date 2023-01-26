package com.geccal.bibliotecainfantil.boundaries.api.model.extension

import com.geccal.bibliotecainfantil.core.domain.pagination.Pagination
import com.geccal.bibliotecainfantil.core.domain.pagination.SearchQuery
import io.ktor.server.request.ApplicationRequest

fun ApplicationRequest.toSearchQuery(
    directionDefault: String,
    sortDefault: String = Pagination.DEFAULT_SORT
): SearchQuery {
    val search = call.request.queryParameters["search"].orEmpty()
    val page = call.request.queryParameters["page"]?.toIntOrNull() ?: Pagination.DEFAULT_PAGE
    val perPage = call.request.queryParameters["perPage"]?.toIntOrNull() ?: Pagination.DEFAULT_PER_PAGE
    val sort = call.request.queryParameters["sort"] ?: sortDefault
    val direction = call.request.queryParameters["direction"] ?: directionDefault
    return SearchQuery(page, perPage, search, sort, direction)
}
