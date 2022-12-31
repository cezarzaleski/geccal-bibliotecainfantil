package com.geccal.bibliotecainfantil.builder

import com.geccal.bibliotecainfantil.core.domain.entity.Book
import com.geccal.bibliotecainfantil.core.domain.entity.BookID
import com.geccal.bibliotecainfantil.core.domain.vo.Author
import com.geccal.bibliotecainfantil.core.domain.vo.Origin
import com.geccal.bibliotecainfantil.core.domain.vo.Publisher
import com.geccal.bibliotecainfantil.core.domain.vo.StatusBook
import java.time.LocalDateTime

object BookBuilder {

    fun build(
        id: BookID = BookID.unique(),
        name: String = "O Céu e o Inferno",
        exemplary: Int = 1,
        status: StatusBook = StatusBook.AVAILABLE,
        edition: String = "1ª",
        year: Int = 2022,
        authors: MutableList<Author> = mutableListOf(Author.create("AuthorFake")),
        publisher: Publisher = Publisher.from("FEB"),
        origin: Origin = Origin.ACQUISITION,
        createdAt: LocalDateTime = LocalDateTime.now(),
        updatedAt: LocalDateTime = LocalDateTime.now(),
        deletedAt: LocalDateTime? = null
    ) = Book.from(
        id = id,
        name = name,
        exemplary = exemplary,
        status = status,
        edition = edition,
        year = year,
        authors = authors,
        publisher = publisher,
        origin = origin,
        createdAt = createdAt,
        updatedAt = updatedAt,
        deletedAt = deletedAt
    )
}
