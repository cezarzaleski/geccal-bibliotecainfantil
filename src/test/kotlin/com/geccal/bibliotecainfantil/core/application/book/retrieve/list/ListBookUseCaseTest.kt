package com.geccal.bibliotecainfantil.core.application.book.retrieve.list

import com.geccal.bibliotecainfantil.builder.BookBuilder
import com.geccal.bibliotecainfantil.core.domain.entity.Book
import com.geccal.bibliotecainfantil.core.domain.pagination.Pagination
import com.geccal.bibliotecainfantil.core.domain.pagination.SearchQuery
import com.geccal.bibliotecainfantil.core.domain.repository.BookRepository
import com.geccal.bibliotecainfantil.core.domain.vo.Publisher
import io.mockk.coEvery
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
internal class ListBookUseCaseTest {
    private val bookRepository = mockk<BookRepository>()
    private val subject = ListBookUseCase(bookRepository)
    private val page = 0
    private val perPage = 1
    private val total = 2L
    private val searchQuery = SearchQuery(page, perPage, "inferno", "name", "ASC")

    @Test
    fun `should list all books with pagination and success`(): Unit = runBlocking {
        val bookFirst = BookBuilder.build(name = "O Céu e o Inferno", publisher = Publisher.from("FEB"))
        val bookSecond = BookBuilder.build(name = "A Bússola e o Leme", publisher = Publisher.from("FEB"))
        val pagination = Pagination(page, perPage, total, items = listOf(bookFirst, bookSecond))
        coEvery { bookRepository.findAll(searchQuery) } returns pagination

        val result = subject.execute(searchQuery)

        assertThat(result.total).isEqualTo(total)
        assertThat(result.perPage).isEqualTo(pagination.perPage)
        assertThat(result.currentPage).isEqualTo(pagination.currentPage)
        assertThat(result.items).isEqualTo(pagination.items.map { BookListOutput.from(it) })
    }

    @Test
    fun `should list all books with pagination empty`(): Unit = runBlocking {
        val pagination = Pagination<Book>(page, perPage, total, items = emptyList())
        coEvery { bookRepository.findAll(searchQuery) } returns pagination

        val result = subject.execute(searchQuery)

        assertThat(result.total).isEqualTo(total)
        assertThat(result.perPage).isEqualTo(pagination.perPage)
        assertThat(result.currentPage).isEqualTo(pagination.currentPage)
        assertThat(result.items).isEmpty()
    }
}
