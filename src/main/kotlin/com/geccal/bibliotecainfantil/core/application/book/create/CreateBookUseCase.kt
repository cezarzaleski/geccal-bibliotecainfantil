package com.geccal.bibliotecainfantil.core.application.book.create

import com.geccal.bibliotecainfantil.core.application.UseCase
import com.geccal.bibliotecainfantil.core.application.exception.ValidationException
import com.geccal.bibliotecainfantil.core.domain.entity.Book
import com.geccal.bibliotecainfantil.core.domain.repository.BookRepository
import com.geccal.bibliotecainfantil.core.domain.validator.handler.NotificationHandler

class CreateBookUseCase(
    private val bookRepository: BookRepository
) : UseCase<CreateBookCommand, CreateBookOutput> {
    override fun execute(input: CreateBookCommand): CreateBookOutput {
        val notificationHandler = NotificationHandler.create()
        val book = notificationHandler.validate {
            Book.create(
                name = input.name,
                exemplary = input.exemplary,
                edition = input.edition,
                year = input.year,
                authors = input.authors,
                origin = input.origin,
                publisher = input.publisher
            )
        }
        return if (notificationHandler.hasError) {
            throw ValidationException(
                "Failed to create a Book Aggregate",
                notificationHandler.getErrors()
            )
        } else create(book!!)
    }

    private fun create(book: Book): CreateBookOutput {
        val createdBook = bookRepository.create(book)
        return CreateBookOutput.from(createdBook)
    }
}
