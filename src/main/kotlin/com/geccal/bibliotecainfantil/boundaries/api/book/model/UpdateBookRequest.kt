package com.geccal.bibliotecainfantil.boundaries.api.book.model

import com.geccal.bibliotecainfantil.core.application.book.update.UpdateBookCommand

data class UpdateBookRequest(
    val name: String?,
    val exemplary: Int?,
    val edition: String?,
    val year: Int?,
    val authors: List<String> = emptyList(),
    val origin: String?,
    val publisher: String?
) {
    fun toCreateBookCommand(id: String?) = UpdateBookCommand(
        id = id.orEmpty(),
        name = name.orEmpty(),
        edition = edition.orEmpty(),
        year = year ?: -1,
        authors = authors,
        origin = origin.orEmpty(),
        publisher = publisher.orEmpty()
    )
}
