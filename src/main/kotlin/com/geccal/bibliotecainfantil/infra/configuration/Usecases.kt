package com.geccal.bibliotecainfantil.infra.configuration

import com.geccal.bibliotecainfantil.core.application.book.create.CreateBookUseCase
import com.geccal.bibliotecainfantil.core.application.book.retrieve.get.GetBookUseCase
import com.geccal.bibliotecainfantil.core.application.book.retrieve.list.ListAuthorUseCase
import com.geccal.bibliotecainfantil.core.application.book.retrieve.list.ListBookUseCase
import com.geccal.bibliotecainfantil.core.application.book.retrieve.list.ListPublisherUseCase
import com.geccal.bibliotecainfantil.core.application.book.update.UpdateBookUseCase
import com.geccal.bibliotecainfantil.infra.database.Repositories

object Usecases {

    val createBookUseCase = CreateBookUseCase(
        Repositories.bookRepository
    )
    val listBookUseCase = ListBookUseCase(
        Repositories.bookRepository
    )
    val updateBookUseCase = UpdateBookUseCase(
        Repositories.bookRepository
    )
    val getBookUseCase = GetBookUseCase(
        Repositories.bookRepository
    )
    val listAuthorUseCase = ListAuthorUseCase(
        Repositories.bookRepository
    )
    val listPublisherUseCase = ListPublisherUseCase(
        Repositories.bookRepository
    )
}
