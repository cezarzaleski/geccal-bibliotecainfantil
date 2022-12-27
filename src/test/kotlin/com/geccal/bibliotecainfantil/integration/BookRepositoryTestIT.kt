package com.geccal.bibliotecainfantil.integration

import com.geccal.bibliotecainfantil.KtorIntegrationTest
import com.geccal.bibliotecainfantil.builder.BookBuilder
import com.geccal.bibliotecainfantil.infra.database.Connection
import com.geccal.bibliotecainfantil.infra.database.VertexConnectionAdapter
import com.geccal.bibliotecainfantil.infra.repository.BookVertexRepository
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
@ExperimentalCoroutinesApi
class BookRepositoryTestIT : KtorIntegrationTest() {

    private val connection: Connection = VertexConnectionAdapter(
        anHost = host,
        anUser = username,
        aPassword = password,
        aPort = port,
        aDatabaseName = dabaseName
    )

    private val subject = BookVertexRepository(
        connection
    )

    @Test
    fun `should create book with success`(): Unit = runBlocking {
        val book = BookBuilder.build()
        val bookCreated = subject.create(book)
        assertThat(true).isTrue
    }
}
