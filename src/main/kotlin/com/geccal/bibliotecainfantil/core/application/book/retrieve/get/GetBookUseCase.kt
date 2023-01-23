package com.geccal.bibliotecainfantil.core.application.book.retrieve.get

import com.geccal.bibliotecainfantil.core.application.UseCase
import com.geccal.bibliotecainfantil.core.domain.entity.Book
import com.geccal.bibliotecainfantil.core.domain.entity.BookID
import com.geccal.bibliotecainfantil.core.domain.repository.BookRepository

class GetBookUseCase(
    private val bookRepository: BookRepository
) : UseCase<String, GetBookOutput> {
    override suspend fun execute(id: String): GetBookOutput {
        return bookRepository.findById(BookID.from(id)).toOutput()
    }

    private fun Book.toOutput() = GetBookOutput.from(this)
}
