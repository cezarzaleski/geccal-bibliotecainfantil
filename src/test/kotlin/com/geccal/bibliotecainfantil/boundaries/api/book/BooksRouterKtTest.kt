package com.geccal.bibliotecainfantil.boundaries.api.book;

import com.geccal.bibliotecainfantil.bibliotecaInfantil
import com.geccal.bibliotecainfantil.builder.BookBuilder
import com.geccal.bibliotecainfantil.builder.CreateBookRequestBuilder
import com.geccal.bibliotecainfantil.core.application.book.create.CreateBookOutput
import com.geccal.bibliotecainfantil.core.application.book.create.CreateBookUseCase
import com.geccal.bibliotecainfantil.infra.extension.toJson
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.server.testing.TestApplication
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
class BooksRouterKtTest {
    private val createBookUseCase: CreateBookUseCase = mockk()

    private val application = TestApplication {
        application { bibliotecaInfantil { booksRouter(createBookUseCase) } }
    }

    @Test
    fun `should create a book with success`(): Unit = runBlocking {
        val expected = CreateBookOutput.from(BookBuilder.build())
        coEvery { createBookUseCase.execute(any()) } returns expected
        val client = application.client

        val response = client.post("/books") {
            contentType(ContentType.Application.Json)
            setBody(CreateBookRequestBuilder.build().toJson())
        }

        assertThat(HttpStatusCode.Created).isEqualTo(response.status)


        coVerify { createBookUseCase.execute(any()) }
        assertThat(expected.toJson()).isEqualTo(response.bodyAsText())
    }

}