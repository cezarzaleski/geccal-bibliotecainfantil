package com.geccal.bibliotecainfantil.core.domain.entity

import com.geccal.bibliotecainfantil.core.domain.validator.handler.NotificationHandler
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Tag
import org.junit.jupiter.api.Test
@Tag("unit")
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
}
