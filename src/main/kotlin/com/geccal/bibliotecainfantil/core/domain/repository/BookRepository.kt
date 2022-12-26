package com.geccal.bibliotecainfantil.core.domain.repository

import com.geccal.bibliotecainfantil.core.domain.entity.Book

interface BookRepository {

    fun create(book: Book): Book
}
