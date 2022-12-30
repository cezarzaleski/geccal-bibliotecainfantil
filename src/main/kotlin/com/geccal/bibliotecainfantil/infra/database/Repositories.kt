package com.geccal.bibliotecainfantil.infra.database

import com.geccal.bibliotecainfantil.core.domain.repository.BookRepository
import com.geccal.bibliotecainfantil.infra.repository.BookVertexRepository

object Repositories {

    val bookRepository: BookRepository = BookVertexRepository(
        ConnectionFactory.vertxConnectionAdapter
    )
}
