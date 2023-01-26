package com.geccal.bibliotecainfantil.core.domain.pagination
import java.util.function.Function

data class Pagination<T>(
    val currentPage: Int = DEFAULT_PAGE,
    val perPage: Int = DEFAULT_PER_PAGE,
    val total: Long,
    val items: List<T>
) {
    fun <R> map(mapper: Function<T, R>): Pagination<R> {
        val aNewList = items.stream().map(mapper).toList()

        return Pagination(currentPage, perPage, total, aNewList)
    }
    companion object {
        const val DEFAULT_PAGE = 0
        const val DEFAULT_PER_PAGE = 30
        const val DEFAULT_SORT = "ASC"
        fun <T> empty(currentPage: Int, perPage: Int) = Pagination<T>(currentPage, perPage, 0L, emptyList())
    }
}
