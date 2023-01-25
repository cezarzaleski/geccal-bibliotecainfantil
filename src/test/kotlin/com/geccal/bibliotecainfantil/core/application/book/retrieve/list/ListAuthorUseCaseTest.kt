package com.geccal.bibliotecainfantil.core.application.book.retrieve.list

import com.geccal.bibliotecainfantil.core.domain.pagination.Pagination
import com.geccal.bibliotecainfantil.core.domain.pagination.SearchQuery
import com.geccal.bibliotecainfantil.core.domain.repository.BookRepository
import io.mockk.coEvery
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
internal class ListAuthorUseCaseTest {
    private val bookRepository = mockk<BookRepository>()
    private val subject = ListAuthorUseCase(bookRepository)
    private val page = 0
    private val perPage = 1
    private val total = 2L
    private val searchQuery = SearchQuery(page, perPage, "maria", "author", "ASC")

    @Test
    fun `should test successful return of paginated authors list`(): Unit = runBlocking {
        val pagination = Pagination(page, perPage, total, items = listOf("Maria", "Pedro"))
        coEvery { bookRepository.findAllAuthors(searchQuery) } returns pagination

        val result = subject.execute(searchQuery)

        assertThat(result.total).isEqualTo(total)
        assertThat(result.perPage).isEqualTo(pagination.perPage)
        assertThat(result.currentPage).isEqualTo(pagination.currentPage)
        assertThat(result.items).isEqualTo(pagination.items.map { it })
    }

    @Test
    fun `should test successful return of paginated authors empty list`(): Unit = runBlocking {
        val pagination = Pagination<String>(page, perPage, total, items = emptyList())
        coEvery { bookRepository.findAllAuthors(searchQuery) } returns pagination

        val result = subject.execute(searchQuery)

        assertThat(result.total).isEqualTo(total)
        assertThat(result.perPage).isEqualTo(pagination.perPage)
        assertThat(result.currentPage).isEqualTo(pagination.currentPage)
        assertThat(result.items).isEmpty()
    }
}
