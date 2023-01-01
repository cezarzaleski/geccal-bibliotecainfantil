package com.geccal.bibliotecainfantil.integration

import com.geccal.bibliotecainfantil.KtorIntegrationTest
import com.geccal.bibliotecainfantil.builder.BookBuilder
import com.geccal.bibliotecainfantil.core.domain.entity.BookID
import com.geccal.bibliotecainfantil.core.domain.exception.NotFoundException
import com.geccal.bibliotecainfantil.core.domain.pagination.SearchQuery
import com.geccal.bibliotecainfantil.core.domain.vo.Publisher
import com.geccal.bibliotecainfantil.infra.repository.BookVertexRepository
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
@ExperimentalCoroutinesApi
class BookRepositoryTestIT : KtorIntegrationTest() {

    private val subject = BookVertexRepository(
        connection
    )
    @BeforeEach
    fun reset() {
        runBlocking {
            cleanUp(listOf("books"))
        }
    }

    @Test
    fun `should create book with success`(): Unit = runBlocking {
        val book = BookBuilder.build()

        val bookCreated = subject.create(book)

        assertThat(bookCreated).isNotNull
    }

    @Test
    fun `should find book by ID with success`(): Unit = runBlocking {
        val book = BookBuilder.build()
        subject.create(book)

        val result = subject.findById(book.id)

        assertThat(result).isNotNull
        assertThat(result.id.value).isEqualTo(book.id.value)
        assertThat(result.name).isEqualTo(book.name)
        assertThat(result.exemplary).isEqualTo(book.exemplary)
        assertThat(result.status).isEqualTo(book.status)
        assertThat(result.edition).isEqualTo(book.edition)
        assertThat(result.year).isEqualTo(book.year)
        assertThat(result.authors).isEqualTo(book.authors)
        assertThat(result.publisher).isEqualTo(book.publisher)
        assertThat(result.origin).isEqualTo(book.origin)
        assertThat(result.createdAt).isEqualTo(book.createdAt)
        assertThat(result.updatedAt).isEqualTo(book.updatedAt)
        assertThat(result.deletedAt).isEqualTo(book.deletedAt)
    }

    @Test
    fun `should throws NotFoundException when find book by ID not exists`(): Unit = runBlocking {
        val book = BookBuilder.build()
        val bookID = BookID.unique()
        subject.create(book)

        assertThrows<NotFoundException> {
            subject.findById(bookID)
        }

    }

    @Test
    fun `should findAll books with pagination search by name with success`(): Unit = runBlocking {
        val bookFirst = BookBuilder.build(name = "O Céu e o Inferno", publisher = Publisher.from("FEB"))
        val bookSecond = BookBuilder.build(name = "A Bússola e o Leme", publisher = Publisher.from("FEB"))
        subject.create(bookFirst)
        subject.create(bookSecond)

        val searchQuery = SearchQuery(0, 1, "inferno", "name", "ASC")
        val result = subject.findAll(searchQuery)

        assertThat(result.total).isEqualTo(1L)
        assertThat(result.items.first().id.value).isEqualTo(bookFirst.id.value)
    }

    @Test
    fun `should findAll books with pagination search by publisher with success`(): Unit = runBlocking {
        val bookFirst = BookBuilder.build(name = "O Céu e o Inferno", publisher = Publisher.from("FEB"))
        val bookSecond = BookBuilder.build(name = "A Bússola e o Leme", publisher = Publisher.from("FEB"))
        subject.create(bookFirst)
        subject.create(bookSecond)

        val searchQuery = SearchQuery(0, 1, "feb", "name", "ASC")
        val result = subject.findAll(searchQuery)

        assertThat(result.currentPage).isEqualTo(0)
        assertThat(result.perPage).isEqualTo(1)
        assertThat(result.total).isEqualTo(2L)
        assertThat(result.items.first().id.value).isEqualTo(bookSecond.id.value)
    }
}
