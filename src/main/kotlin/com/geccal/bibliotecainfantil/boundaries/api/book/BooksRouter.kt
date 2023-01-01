package com.geccal.bibliotecainfantil.boundaries.api.book

import com.geccal.bibliotecainfantil.boundaries.api.book.models.CreateBookRequest
import com.geccal.bibliotecainfantil.boundaries.api.book.models.extension.toSearchQuery
import com.geccal.bibliotecainfantil.core.application.book.create.CreateBookUseCase
import com.geccal.bibliotecainfantil.core.application.book.retrieve.list.ListBookUseCase
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route

fun Routing.booksRouter(
    createBookUseCase: CreateBookUseCase,
    listBookUseCase: ListBookUseCase
) {
    route("/books") {
        post {
            val createBookRequest = call.receive<CreateBookRequest>()
            call.respond(
                HttpStatusCode.Created,
                createBookUseCase.execute(
                    createBookRequest.toCreateBookCommand()
                )
            )
        }
        get {
            call.respond(
                HttpStatusCode.OK,
                listBookUseCase.execute(call.request.toSearchQuery())
            )
        }
    }
}
