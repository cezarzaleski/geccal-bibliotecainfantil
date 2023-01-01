package com.geccal.bibliotecainfantil.core.application.book.create

import com.geccal.bibliotecainfantil.builder.BookBuilder
import com.geccal.bibliotecainfantil.builder.CreateBookCommandBuilder
import com.geccal.bibliotecainfantil.core.domain.repository.BookRepository
import io.mockk.coEvery
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
internal class CreateBookUseCaseTest {
    private val bookRepository = mockk<BookRepository>()
    private val subject = CreateBookUseCase(bookRepository)

    @Test
    fun `should create Book with success`(): Unit = runBlocking {
        val createBookCommand = CreateBookCommandBuilder.build()
        val book = BookBuilder.build()

        coEvery { bookRepository.create(any()) } returns book

        val result = subject.execute(createBookCommand)

        assertThat(result.name).isEqualTo(book.name)
        assertThat(result.exemplary).isEqualTo(book.exemplary)
        assertThat(result.edition).isEqualTo(book.edition)
        assertThat(result.year).isEqualTo(book.year)
        assertThat(result.publisher).isEqualTo(book.publisher.value)
        assertThat(result.origin).isEqualTo(book.origin.name)
    }
}
