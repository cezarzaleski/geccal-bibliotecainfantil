package com.geccal.bibliotecainfantil.infra.repository

import com.geccal.bibliotecainfantil.IntegrationDatabaseTest
import com.geccal.bibliotecainfantil.builder.BookBuilder
import com.geccal.bibliotecainfantil.core.domain.entity.BookID
import com.geccal.bibliotecainfantil.core.domain.exception.NotFoundException
import com.geccal.bibliotecainfantil.core.domain.pagination.SearchQuery
import com.geccal.bibliotecainfantil.core.domain.vo.Author
import com.geccal.bibliotecainfantil.core.domain.vo.Publisher
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

@ExtendWith(MockKExtension::class)
@ExperimentalCoroutinesApi
class BookRepositoryComponentTest : IntegrationDatabaseTest() {

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
    fun `should find book by id with success`(): Unit = runBlocking {
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

    @Test
    fun `should update a book with success`(): Unit = runBlocking {
        val book = BookBuilder.build()
        val nameExpected = "O Céu e o Inferno update"
        val editionExpected = "2ª"
        val yearExpected = 2023
        val authorsExpected = mutableListOf("AuthorFakeUpdate")
        val publisherExpected = "Boa Nova"
        val originExpected = "CONFECTION"
        val updatedAtExpected = LocalDateTime.now().plusDays(1).truncatedTo(ChronoUnit.MICROS)
        subject.create(book)
        book.update(
            name = nameExpected,
            edition = editionExpected,
            year = yearExpected,
            authors = authorsExpected,
            origin = originExpected,
            publisher = publisherExpected,
            updatedAt = updatedAtExpected
        )

        subject.update(book)

        val result = subject.findById(book.id)

        assertThat(result).isNotNull
        assertThat(result.id.value).isEqualTo(book.id.value)
        assertThat(result.name).isEqualTo(nameExpected)
        assertThat(result.exemplary).isEqualTo(book.exemplary)
        assertThat(result.status).isEqualTo(book.status)
        assertThat(result.edition).isEqualTo(editionExpected)
        assertThat(result.year).isEqualTo(yearExpected)
        assertThat(result.publisher.value).isEqualTo(publisherExpected)
        assertThat(result.authors.map { it.value }).isEqualTo(authorsExpected)
        assertThat(result.origin.name).isEqualTo(originExpected)
        assertThat(result.createdAt).isEqualTo(book.createdAt)
        assertThat(result.updatedAt).isEqualTo(updatedAtExpected)
        assertThat(result.deletedAt).isEqualTo(book.deletedAt)
    }

    @Test
    fun `should findAll authors with pagination search by name with success`(): Unit = runBlocking {
        val firstBook = BookBuilder.build(
            name = "O Céu e o Inferno",
            authors = mutableListOf(Author.create("Divaldo"), Author.create("Pereira"))
        )
        val secondBook = BookBuilder.build(
            name = "A Bússola e o Leme",
            authors = mutableListOf(Author.create("Maria"), Author.create("Pedro"))
        )
        subject.create(firstBook)
        subject.create(secondBook)

        val searchQuery = SearchQuery(0, 1, "maria", "author", "ASC")
        val result = subject.findAllAuthors(searchQuery)

        assertThat(result.total).isEqualTo(1L)
        assertThat(result.items.first()).isEqualTo("Maria")
    }
}
