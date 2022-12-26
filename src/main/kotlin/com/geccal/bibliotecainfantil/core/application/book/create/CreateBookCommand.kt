package com.geccal.bibliotecainfantil.core.application.book.create

data class CreateBookCommand(
    val name: String,
    val exemplary: Int,
    val edition: String,
    val year: Int,
    val authors: List<String>,
    val origin: String,
    val publisher: String
)
