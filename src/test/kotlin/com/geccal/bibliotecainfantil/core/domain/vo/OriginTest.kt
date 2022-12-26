package com.geccal.bibliotecainfantil.core.domain.vo

import com.geccal.bibliotecainfantil.core.domain.validator.handler.NotificationHandler
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

internal class OriginTest {

    @Test
    fun `should create Origin with success`() {
        val nameExpected = "ACQUISITION"
        val origin = Origin.create(nameExpected)

        assertThat(origin.name).isEqualTo(nameExpected)
    }

    @Test
    fun `should throw when create Origin with invalid name`() {
        val nameExpected = "FAKE"
        val notification = NotificationHandler.create()

        notification.validate {
            Origin.create(nameExpected)
        }

        assertThat(notification.getErrors()).isNotEmpty
        assertThat(notification.getErrors().first().message).isEqualTo("origin: Is invalid")
    }

    @Test
    fun `should throw when create Origin with empty`() {
        val nameExpected = ""
        val notification = NotificationHandler.create()

        notification.validate {
            Origin.create(nameExpected)
        }

        assertThat(notification.getErrors()).isNotEmpty
        assertThat(notification.getErrors().first().message).isEqualTo("origin: Is empty")
    }
}
