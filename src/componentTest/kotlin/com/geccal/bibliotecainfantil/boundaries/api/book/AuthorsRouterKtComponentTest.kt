package com.geccal.bibliotecainfantil.boundaries.api.book

import com.geccal.bibliotecainfantil.bibliotecaInfantil
import com.geccal.bibliotecainfantil.core.application.book.retrieve.list.ListAuthorUseCase
import com.geccal.bibliotecainfantil.core.domain.pagination.Pagination
import com.geccal.bibliotecainfantil.infra.extension.toJson
import io.ktor.client.request.get
import io.ktor.client.request.parameter
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
class AuthorsRouterKtComponentTest {
    private val listAuthorUseCase: ListAuthorUseCase = mockk()

    private val application = TestApplication {
        application {
            bibliotecaInfantil {
                authorsRouter(listAuthorUseCase)
            }
        }
    }

    @Test
    fun `should test successful return of paginated authors list`(): Unit = runBlocking {
        val client = application.client
        val items = listOf("Maria")
        val expected = Pagination(total = 1L, items = items)
        coEvery { listAuthorUseCase.execute(any()) } returns expected

        val response = client.get("/authors") {
            contentType(ContentType.Application.Json)
            parameter("page", expected.currentPage)
            parameter("perPage", expected.perPage)
            parameter("sort", Pagination.DEFAULT_SORT)
            parameter("direction", "author")
        }

        assertThat(HttpStatusCode.OK).isEqualTo(response.status)

        coVerify { listAuthorUseCase.execute(any()) }
        assertThat(expected.toJson()).isEqualTo(response.bodyAsText())
    }
}
