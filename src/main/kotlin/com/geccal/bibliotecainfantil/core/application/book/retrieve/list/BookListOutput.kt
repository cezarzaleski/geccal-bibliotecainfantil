package com.geccal.bibliotecainfantil.core.application.book.retrieve.list

import com.geccal.bibliotecainfantil.core.domain.entity.Book

data class BookListOutput(
    val id: String,
    val name: String,
    val exemplary: Int,
    val edition: String,
    val year: Int,
    val authors: List<String>,
    val origin: String,
    val publisher: String
) {
    companion object {
        fun from(book: Book): BookListOutput = BookListOutput(
            id = book.id.value,
            name = book.name,
            exemplary = book.exemplary,
            edition = book.edition,
            year = book.year,
            authors = book.authors.map { it.value },
            origin = book.origin.name,
            publisher = book.publisher.value,
        )
    }
}
