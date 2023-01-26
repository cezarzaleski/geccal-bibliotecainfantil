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
internal class ListPublisherUseCaseTest {
    private val bookRepository = mockk<BookRepository>()
    private val subject = ListPublisherUseCase(bookRepository)
    private var total = 2L
    private val searchQuery = SearchQuery(terms = "feb", sort = "publisher")

    @Test
    fun `should test successful return of paginated publishers list`(): Unit = runBlocking {
        val pagination = Pagination(total = total, items = listOf("Boa Nova", "FEB"))
        coEvery { bookRepository.findAllPublishers(searchQuery) } returns pagination

        val result = subject.execute(searchQuery)

        assertThat(result.total).isEqualTo(total)
        assertThat(result.perPage).isEqualTo(pagination.perPage)
        assertThat(result.currentPage).isEqualTo(pagination.currentPage)
        assertThat(result.items).isEqualTo(pagination.items.map { it })
    }

    @Test
    fun `should test successful return of paginated publishers empty list`(): Unit = runBlocking {
        total = 0L
        val pagination = Pagination<String>(total = total, items = emptyList())
        coEvery { bookRepository.findAllPublishers(searchQuery) } returns pagination

        val result = subject.execute(searchQuery)

        assertThat(result.total).isEqualTo(total)
        assertThat(result.perPage).isEqualTo(pagination.perPage)
        assertThat(result.currentPage).isEqualTo(pagination.currentPage)
        assertThat(result.items).isEmpty()
    }
}
