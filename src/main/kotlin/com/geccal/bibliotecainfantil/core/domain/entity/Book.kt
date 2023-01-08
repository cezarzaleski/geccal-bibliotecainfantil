package com.geccal.bibliotecainfantil.core.domain.entity

import com.geccal.bibliotecainfantil.core.domain.AggregateRoot
import com.geccal.bibliotecainfantil.core.domain.exception.NotificationException
import com.geccal.bibliotecainfantil.core.domain.validator.BookValidate
import com.geccal.bibliotecainfantil.core.domain.validator.ValidationHandler
import com.geccal.bibliotecainfantil.core.domain.validator.handler.NotificationHandler
import com.geccal.bibliotecainfantil.core.domain.vo.Author
import com.geccal.bibliotecainfantil.core.domain.vo.Origin
import com.geccal.bibliotecainfantil.core.domain.vo.Publisher
import com.geccal.bibliotecainfantil.core.domain.vo.StatusBook
import java.time.LocalDateTime

@Suppress("LongParameterList")
class Book private constructor(
    override val id: BookID,
    var name: String,
    val exemplary: Int,
    val status: StatusBook,
    var edition: String,
    var year: Int,
    var authors: MutableList<Author>,
    var publisher: Publisher,
    var origin: Origin,
    val createdAt: LocalDateTime,
    var updatedAt: LocalDateTime,
    var deletedAt: LocalDateTime? = null
) : AggregateRoot<BookID>(id) {

    companion object {
        fun create(
            name: String,
            exemplary: Int,
            edition: String,
            year: Int,
            authors: List<String>,
            origin: String,
            publisher: String
        ): Book {
            val now = LocalDateTime.now()
            val notification = NotificationHandler.create()
            val origin = notification.validate { Origin.create(origin) } ?: Origin.UNKNOWN
            val publisher = notification.validate { Publisher.create(publisher) } ?: Publisher.from("Invalid")
            val authors = authors.map { Author.create(it) }.toMutableList()

            val book = Book(
                id = BookID.unique(),
                name = name,
                exemplary = exemplary,
                status = StatusBook.AVAILABLE,
                edition = edition,
                year = year,
                authors = authors,
                publisher = publisher,
                origin = origin,
                createdAt = now,
                updatedAt = now,
            )
            book.selfValidate(notification)
            return book
        }

        fun from(
            id: BookID,
            name: String,
            exemplary: Int,
            status: StatusBook,
            edition: String,
            year: Int,
            authors: MutableList<Author>,
            publisher: Publisher,
            origin: Origin,
            createdAt: LocalDateTime,
            updatedAt: LocalDateTime,
            deletedAt: LocalDateTime? = null
        ) = Book(
            id = id,
            name = name,
            exemplary = exemplary,
            status = status,
            edition = edition,
            year = year,
            authors = authors,
            publisher = publisher,
            origin = origin,
            createdAt = createdAt,
            updatedAt = updatedAt,
            deletedAt = deletedAt
        )
    }

    fun delete() {
        deletedAt = LocalDateTime.now()
    }

    private fun selfValidate(notification: ValidationHandler) {
        validate(notification)
        id.validate(notification)
        publisher.validate(notification)
        authors.forEach { it.validate(notification) }
        if (notification.hasError) {
            throw NotificationException("Failed to create a Aggregate Book", notification as NotificationHandler)
        }
    }

    override fun validate(handler: ValidationHandler) {
        BookValidate(this, handler).validate()
    }

    fun update(
        name: String,
        edition: String,
        year: Int,
        authors: List<String>,
        origin: String,
        publisher: String,
        updatedAt: LocalDateTime = LocalDateTime.now()
    ): Book {
        val notification = NotificationHandler.create()
        val origin = notification.validate { Origin.create(origin) } ?: Origin.UNKNOWN
        val authors = authors.map { Author.create(it) }.toMutableList()
        val publisher = notification.validate { Publisher.create(publisher) } ?: Publisher.from("Invalid")

        this.name = name
        this.edition = edition
        this.year = year
        this.authors = authors
        this.publisher = publisher
        this.origin = origin
        this.updatedAt = updatedAt
        this.selfValidate(notification)
        return this
    }
}
