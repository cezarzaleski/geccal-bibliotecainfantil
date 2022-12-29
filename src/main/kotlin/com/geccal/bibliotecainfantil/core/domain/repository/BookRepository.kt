package com.geccal.bibliotecainfantil.core.domain.repository

import com.geccal.bibliotecainfantil.core.domain.entity.Book
import com.geccal.bibliotecainfantil.core.domain.entity.BookID

interface BookRepository {

    suspend fun create(book: Book): Book
    suspend fun findById(id: BookID): Book
}
