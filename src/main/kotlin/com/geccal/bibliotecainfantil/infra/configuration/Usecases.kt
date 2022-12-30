package com.geccal.bibliotecainfantil.infra.configuration

import com.geccal.bibliotecainfantil.core.application.book.create.CreateBookUseCase
import com.geccal.bibliotecainfantil.infra.database.Repositories

object Usecases {

    val createBookUseCase = CreateBookUseCase(
        Repositories.bookRepository
    )
}
