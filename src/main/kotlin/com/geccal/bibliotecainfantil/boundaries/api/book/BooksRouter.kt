package com.geccal.bibliotecainfantil.boundaries.api.book

import com.geccal.bibliotecainfantil.boundaries.api.book.model.CreateBookRequest
import com.geccal.bibliotecainfantil.boundaries.api.book.model.UpdateBookRequest
import com.geccal.bibliotecainfantil.boundaries.api.model.extension.toSearchQuery
import com.geccal.bibliotecainfantil.core.application.book.create.CreateBookUseCase
import com.geccal.bibliotecainfantil.core.application.book.retrieve.get.GetBookUseCase
import com.geccal.bibliotecainfantil.core.application.book.retrieve.list.ListBookUseCase
import com.geccal.bibliotecainfantil.core.application.book.update.UpdateBookUseCase
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import io.ktor.server.routing.route

fun Routing.booksRouter(
    createBookUseCase: CreateBookUseCase,
    listBookUseCase: ListBookUseCase,
    updateBookUseCase: UpdateBookUseCase,
    getBookUseCase: GetBookUseCase
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
        get("/{id}") {
            val id = call.parameters["id"].orEmpty()
            call.respond(
                HttpStatusCode.OK,
                getBookUseCase.execute(id)
            )
        }
        put("/{id}") {
            val updateBookRequest = call.receive<UpdateBookRequest>()
            val id = call.parameters["id"]
            updateBookUseCase.execute(
                updateBookRequest.toCreateBookCommand(id)
            )
            call.respond(HttpStatusCode.NoContent)
        }
    }
}
