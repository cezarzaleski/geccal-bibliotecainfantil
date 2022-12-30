package com.geccal.bibliotecainfantil.boundaries.api.book.models

import com.geccal.bibliotecainfantil.core.application.book.create.CreateBookCommand

data class CreateBookRequest(
    val name: String?,
    val exemplary: Int?,
    val edition: String?,
    val year: Int?,
    val authors: List<String> = emptyList(),
    val origin: String?,
    val publisher: String?
) {
    fun toCreateBookCommand() = CreateBookCommand(
        name = name.orEmpty(),
        exemplary = exemplary ?: -1,
        edition = edition.orEmpty(),
        year = year ?: -1,
        authors = authors,
        origin = origin.orEmpty(),
        publisher = publisher.orEmpty()
    )
}