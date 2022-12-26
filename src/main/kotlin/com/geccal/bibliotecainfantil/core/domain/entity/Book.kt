package com.geccal.bibliotecainfantil.core.domain.entity

import com.geccal.bibliotecainfantil.core.domain.AggregateRoot
import com.geccal.bibliotecainfantil.core.domain.exception.NotificationException
import com.geccal.bibliotecainfantil.core.domain.validator.BookValidate
import com.geccal.bibliotecainfantil.core.domain.vo.Author
import com.geccal.bibliotecainfantil.core.domain.vo.Origin
import com.geccal.bibliotecainfantil.core.domain.vo.Publisher
import com.geccal.bibliotecainfantil.core.domain.vo.StatusBook
import com.geccal.bibliotecainfantil.core.domain.validator.ValidationHandler
import com.geccal.bibliotecainfantil.core.domain.validator.handler.NotificationHandler
import java.time.LocalDateTime

class Book private constructor(
    override val id: BookID,
    val name: String,
    val exemplary: Int,
    val status: StatusBook,
    val edition: String,
    val year: Int,
    val authors: MutableList<Author>,
    val publisher: Publisher,
    val origin: Origin,
    val createdAt: LocalDateTime,
    val updatedAt: LocalDateTime,
    val deletedAt: LocalDateTime? = null
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
            val authors = authors.map { Author.create(it) }.toMutableList()

            val book = Book(
                id = BookID.unique(),
                name = name,
                exemplary = exemplary,
                status = StatusBook.AVAILABLE,
                edition = edition,
                year = year,
                authors = authors,
                publisher = Publisher.create(publisher),
                origin = origin,
                createdAt = now,
                updatedAt = now,
            )
            book.selfValidate(notification)
            return book
        }
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
}


