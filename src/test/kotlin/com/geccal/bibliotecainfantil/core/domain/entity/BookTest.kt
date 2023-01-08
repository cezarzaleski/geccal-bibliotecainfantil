package com.geccal.bibliotecainfantil.core.domain.entity

import com.geccal.bibliotecainfantil.builder.BookBuilder
import com.geccal.bibliotecainfantil.core.domain.validator.handler.NotificationHandler
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

internal class BookTest {

    private val publisherExpected = "FEB"

    @Test
    fun `validator should return errors when validation fails`() {
        val notification = NotificationHandler.create()
        val nameExpected = ""
        val exemplaryExpected = 1
        val editionExpected = "1ª"
        val yearExpected = 2022
        val originExpected = "ACQUISITION"

        notification.validate {
            Book.create(
                name = nameExpected,
                exemplary = exemplaryExpected,
                edition = editionExpected,
                year = yearExpected,
                authors = emptyList(),
                origin = originExpected,
                publisher = publisherExpected
            )
        }
        assertThat(notification.getErrors()).isNotEmpty
        assertThat(notification.getErrors().first().message).isEqualTo("name: Is empty")
    }

    @Test
    fun `should create a new Book with success`() {
        val nameExpected = "BookFake"
        val exemplaryExpected = 1
        val editionExpected = "1ª"
        val yearExpected = 2022
        val originExpected = "CONFECTION"

        val book = Book.create(
            name = nameExpected,
            exemplary = exemplaryExpected,
            edition = editionExpected,
            year = yearExpected,
            authors = emptyList(),
            origin = originExpected,
            publisher = publisherExpected
        )
        assertThat(book.name).isEqualTo(nameExpected)
        assertThat(book.exemplary).isEqualTo(exemplaryExpected)
        assertThat(book.edition).isEqualTo(editionExpected)
        assertThat(book.year).isEqualTo(yearExpected)
        assertThat(book.origin.name).isEqualTo(originExpected)
    }

    @Test
    fun `validator should return errors when origin is invalid`() {
        val notification = NotificationHandler.create()
        val nameExpected = "name"
        val exemplaryExpected = 1
        val editionExpected = "1ª"
        val yearExpected = 2022
        val originExpected = "FAKE"

        notification.validate {
            Book.create(
                name = nameExpected,
                exemplary = exemplaryExpected,
                edition = editionExpected,
                year = yearExpected,
                authors = emptyList(),
                origin = originExpected,
                publisher = publisherExpected
            )
        }
        assertThat(notification.getErrors()).isNotEmpty
        assertThat(notification.getErrors().first().message).isEqualTo("origin: Is invalid")
    }

    @Test
    fun `should update a Book with success`() {
        val subject = BookBuilder.build()
        val nameExpected = "BookFakeUpdate"
        val editionExpected = "2ª"
        val yearExpected = 2021
        val originExpected = "CONFECTION"

        val book = subject.update(
            name = nameExpected,
            edition = editionExpected,
            year = yearExpected,
            authors = listOf("Update Author"),
            origin = originExpected,
            publisher = publisherExpected
        )
        assertThat(book.name).isEqualTo(nameExpected)
        assertThat(book.edition).isEqualTo(editionExpected)
        assertThat(book.year).isEqualTo(yearExpected)
        assertThat(book.origin.name).isEqualTo(originExpected)
    }

    @Test
    fun `validator should return errors when origin is invalid to update a book`() {
        val subject = BookBuilder.build()
        val notification = NotificationHandler()
        val nameExpected = "BookFakeUpdate"
        val editionExpected = "2ª"
        val yearExpected = 2021
        val originExpected = "ERROR"

        notification.validate {
            subject.update(
                name = nameExpected,
                edition = editionExpected,
                year = yearExpected,
                authors = listOf("Update Author"),
                origin = originExpected,
                publisher = publisherExpected
            )
        }
        assertThat(notification.getErrors()).isNotEmpty
        assertThat(notification.getErrors().first().message).isEqualTo("origin: Is invalid")
    }
}
