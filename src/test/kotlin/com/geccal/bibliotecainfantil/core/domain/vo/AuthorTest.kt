package com.geccal.bibliotecainfantil.core.domain.vo

import com.geccal.bibliotecainfantil.core.domain.validator.handler.NotificationHandler
import org.assertj.core.api.Assertions
import org.junit.Test


internal class AuthorTest {

    private val notification = NotificationHandler.create()

    @Test
    fun `should create Author with success`() {
        val nameExpected = "author"
        val author = Author.create(nameExpected)
        author.validate(notification)

        Assertions.assertThat(author.value).isEqualTo(nameExpected)
    }

    @Test
    fun `should throw when create Author with empty`() {
        val emptyName = ""

        Author.create(emptyName)
            .validate(notification)

        Assertions.assertThat(notification.getErrors()).isNotEmpty
        Assertions.assertThat(notification.getErrors().first().message).isEqualTo("author: Is empty")
    }

}