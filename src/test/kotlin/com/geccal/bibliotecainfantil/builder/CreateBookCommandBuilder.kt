package com.geccal.bibliotecainfantil.builder

import com.geccal.bibliotecainfantil.core.application.book.create.CreateBookCommand

object CreateBookCommandBuilder {

    fun build(
        name: String = "O Céu e o Inferno",
        exemplary: Int = 1,
        edition: String = "1ª",
        year: Int = 2022,
        authors: List<String> = listOf("Divaldo Franco"),
        origin: String = "ACQUISITION",
        publisher: String = "FEB"
    ) = CreateBookCommand(
        name = name,
        exemplary = exemplary,
        edition = edition,
        year = year,
        authors = authors,
        origin = origin,
        publisher = publisher
    )
}
