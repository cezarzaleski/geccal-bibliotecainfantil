package com.geccal.bibliotecainfantil.boundaries.api.model.extension

import com.geccal.bibliotecainfantil.core.domain.pagination.Pagination
import com.geccal.bibliotecainfantil.core.domain.pagination.SearchQuery
import io.ktor.server.request.ApplicationRequest

fun ApplicationRequest.toSearchQuery(): SearchQuery {
    val search = call.request.queryParameters["search"].orEmpty()
    val page = call.request.queryParameters["page"]?.toIntOrNull() ?: Pagination.DEFAULT_PAGE
    val perPage = call.request.queryParameters["perPage"]?.toIntOrNull() ?: Pagination.DEFAULT_PER_PAGE
    val sort = call.request.queryParameters["sort"].orEmpty()
    val direction = call.request.queryParameters["direction"] ?: "name"
    return SearchQuery(page, perPage, search, sort, direction)
}
