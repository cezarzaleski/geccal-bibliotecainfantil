package com.geccal.bibliotecainfantil.core.domain.pagination

data class SearchQuery(
    val page: Int = Pagination.DEFAULT_PAGE,
    val perPage: Int = Pagination.DEFAULT_PER_PAGE,
    val terms: String,
    val sort: String,
    val direction: String = Pagination.DEFAULT_DIRECTION
)
