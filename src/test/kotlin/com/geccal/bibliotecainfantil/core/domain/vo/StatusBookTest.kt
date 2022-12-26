package com.geccal.bibliotecainfantil.core.domain.vo

import com.geccal.bibliotecainfantil.core.domain.validator.handler.NotificationHandler
import org.assertj.core.api.Assertions
import org.junit.Test


internal class StatusBookTest {

    @Test
    fun `should create StatusBook with success`() {
        val nameExpected = "AVAILABLE"
        val origin = StatusBook.create(nameExpected)

        Assertions.assertThat(origin.name).isEqualTo(nameExpected)
    }

    @Test
    fun `should throw when create StatusBook with invalid name`() {
        val nameExpected = "FAKE"
        val notification = NotificationHandler.create()

        notification.validate {
            StatusBook.create(nameExpected)
        }

        Assertions.assertThat(notification.getErrors()).isNotEmpty
        Assertions.assertThat(notification.getErrors().first().message).isEqualTo("status: Is invalid")
    }

    @Test
    fun `should throw when create StatusBook with empty`() {
        val nameExpected = ""
        val notification = NotificationHandler.create()

        notification.validate {
            StatusBook.create(nameExpected)
        }

        Assertions.assertThat(notification.getErrors()).isNotEmpty
        Assertions.assertThat(notification.getErrors().first().message).isEqualTo("status: Is empty")
    }
}