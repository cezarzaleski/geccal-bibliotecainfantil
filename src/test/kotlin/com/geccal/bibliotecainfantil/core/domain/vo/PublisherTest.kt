package com.geccal.bibliotecainfantil.core.domain.vo

import com.geccal.bibliotecainfantil.core.domain.validator.handler.NotificationHandler
import org.assertj.core.api.Assertions
import org.junit.Test

internal class PublisherTest {

    private val notification = NotificationHandler.create()

    @Test
    fun `should create Publisher with success`() {
        val nameExpected = "publisher"
        val publisher = Publisher.create(nameExpected)
        publisher.validate(notification)

        Assertions.assertThat(publisher.value).isEqualTo(nameExpected)
    }

    @Test
    fun `should throw when create Publisher with empty`() {
        val emptyName = ""

        Publisher.create(emptyName)
            .validate(notification)

        Assertions.assertThat(notification.getErrors()).isNotEmpty
        Assertions.assertThat(notification.getErrors().first().message).isEqualTo("publisher: Is empty")
    }
}
