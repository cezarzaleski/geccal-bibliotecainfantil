package com.geccal.bibliotecainfantil.core.application.book.retrieve.get

import com.geccal.bibliotecainfantil.builder.BookBuilder
import com.geccal.bibliotecainfantil.core.domain.entity.BookID
import com.geccal.bibliotecainfantil.core.domain.exception.NotFoundException
import com.geccal.bibliotecainfantil.core.domain.repository.BookRepository
import com.geccal.bibliotecainfantil.core.domain.vo.Publisher
import io.mockk.coEvery
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
internal class GetBookUseCaseTest {
    private val bookRepository = mockk<BookRepository>()
    private val subject = GetBookUseCase(bookRepository)

    private val bookId = BookID.unique()
    private val book = BookBuilder.build(
        id = bookId,
        name = "O Céu e o Inferno",
        publisher = Publisher.from("FEB")
    )

    @Test
    fun `should return a Book with success`(): Unit = runBlocking {
        coEvery { bookRepository.findById(any()) } returns book

        val result = subject.execute(bookId.value)

        assertThat(result.id).isEqualTo(bookId.value)
        assertThat(result.name).isEqualTo(book.name)
        assertThat(result.exemplary).isEqualTo(book.exemplary)
        assertThat(result.edition).isEqualTo(book.edition)
        assertThat(result.year).isEqualTo(book.year)
        assertThat(result.origin).isEqualTo(book.origin.name)
        assertThat(result.publisher).isEqualTo(book.publisher.value)
        assertThat(result.authors).isEqualTo(book.authors.map { it.value })
    }

    @Test
    fun `should thrown NotFound exception when not exists a book by ID`(): Unit = runBlocking {
        coEvery { bookRepository.findById(any()) } throws NotFoundException.from("Book", bookId)

        assertThrows<NotFoundException> {
            subject.execute(bookId.value)
        }
    }
}
