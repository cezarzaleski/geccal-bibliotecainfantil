package com.geccal.bibliotecainfantil.core.application.book.update

import com.geccal.bibliotecainfantil.core.application.UnitUseCase
import com.geccal.bibliotecainfantil.core.application.exception.ValidationException
import com.geccal.bibliotecainfantil.core.domain.entity.BookID
import com.geccal.bibliotecainfantil.core.domain.repository.BookRepository
import com.geccal.bibliotecainfantil.core.domain.validator.handler.NotificationHandler

class UpdateBookUseCase(
    private val bookRepository: BookRepository
) : UnitUseCase<UpdateBookCommand> {
    override suspend fun execute(input: UpdateBookCommand) {
        val notificationHandler = NotificationHandler.create()
        val bookId = BookID.from(input.id)
        val book = bookRepository.findById(bookId)
        notificationHandler.validate {
            book.update(
                name = input.name,
                edition = input.edition,
                year = input.year,
                authors = input.authors,
                origin = input.origin,
                publisher = input.publisher
            )
        }
        if (notificationHandler.hasError) {
            throw ValidationException(
                "Failed to update a Book Aggregate",
                notificationHandler.getErrors()
            )
        }
        bookRepository.update(book)
    }
}
