package com.geccal.bibliotecainfantil.core.domain.pagination

data class Pagination<T>(
    val currentPage: Int,
    val perPage: Int,
    val total: Long,
    val items: List<T>
) {
    companion object {
        fun <T> empty(currentPage: Int, perPage: Int) = Pagination<T>(currentPage, perPage, 0L, emptyList())
    }
}
