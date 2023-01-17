package com.geccal.bibliotecainfantil.core.application.book.update

data class UpdateBookCommand(
    val id: String,
    val name: String,
    val edition: String,
    val year: Int,
    val authors: List<String>,
    val origin: String,
    val publisher: String
)
