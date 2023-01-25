package com.geccal.bibliotecainfantil.core.domain.repository

import com.geccal.bibliotecainfantil.core.domain.entity.Book
import com.geccal.bibliotecainfantil.core.domain.entity.BookID
import com.geccal.bibliotecainfantil.core.domain.pagination.Pagination
import com.geccal.bibliotecainfantil.core.domain.pagination.SearchQuery

interface BookRepository {

    suspend fun create(book: Book): Book
    suspend fun findById(id: BookID): Book
    suspend fun findAll(query: SearchQuery): Pagination<Book>
    suspend fun findAllAuthors(query: SearchQuery): Pagination<String>
    suspend fun update(book: Book): Book
}
