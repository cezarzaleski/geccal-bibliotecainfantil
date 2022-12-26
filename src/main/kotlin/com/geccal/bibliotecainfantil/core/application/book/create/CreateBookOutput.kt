package com.geccal.bibliotecainfantil.core.application.book.create

import com.geccal.bibliotecainfantil.core.domain.entity.Book

data class CreateBookOutput(
    val name: String,
    val exemplary: Int,
    val edition: String,
    val year: Int,
    val authors: List<String>,
    val origin: String,
    val publisher: String
) {
    companion object {
        fun from(book: Book): CreateBookOutput = CreateBookOutput(
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
