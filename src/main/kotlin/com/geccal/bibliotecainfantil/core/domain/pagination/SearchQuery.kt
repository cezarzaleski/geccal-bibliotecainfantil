package com.geccal.bibliotecainfantil.core.domain.pagination

data class SearchQuery(
    val page: Int,
    val perPage: Int,
    val terms: String,
    val sort: String,
    val direction: String = Pagination.DEFAULT_DIRECTION
)
