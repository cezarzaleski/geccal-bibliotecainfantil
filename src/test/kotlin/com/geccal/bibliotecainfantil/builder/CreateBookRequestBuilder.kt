package com.geccal.bibliotecainfantil.builder

import com.geccal.bibliotecainfantil.boundaries.api.book.models.CreateBookRequest
import com.geccal.bibliotecainfantil.core.application.book.create.CreateBookCommand

object CreateBookRequestBuilder {

    fun build(
        name: String = "O Céu e o Inferno",
        exemplary: Int = 1,
        edition: String = "1ª",
        year: Int = 2022,
        authors: List<String> = listOf("Divaldo Franco"),
        origin: String = "ACQUISITION",
        publisher: String = "FEB"
    ) = CreateBookRequest(
        name = name,
        exemplary = exemplary,
        edition = edition,
        year = year,
        authors = authors,
        origin = origin,
        publisher = publisher
    )
}
