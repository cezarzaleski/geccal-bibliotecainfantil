package com.geccal.bibliotecainfantil.core.application.book.retrieve.list

import com.geccal.bibliotecainfantil.core.application.UseCase
import com.geccal.bibliotecainfantil.core.domain.pagination.Pagination
import com.geccal.bibliotecainfantil.core.domain.pagination.SearchQuery
import com.geccal.bibliotecainfantil.core.domain.repository.BookRepository

class ListBookUseCase(
    private val bookRepository: BookRepository
) : UseCase<SearchQuery, Pagination<BookListOutput>> {
    override suspend fun execute(input: SearchQuery): Pagination<BookListOutput> {
        return bookRepository
            .findAll(query = input)
            .map{ BookListOutput.from(it)}
    }
}
