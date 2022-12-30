package com.geccal.bibliotecainfantil.boundaries.api.book

import com.geccal.bibliotecainfantil.boundaries.api.book.models.CreateBookRequest
import com.geccal.bibliotecainfantil.core.application.book.create.CreateBookUseCase
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.routing.Routing
import io.ktor.server.routing.post
import io.ktor.server.routing.route

fun Routing.booksRouter(createBookUseCase: CreateBookUseCase) {
    route("/books") {
        post {
            val segmentationRequest = call.receive<CreateBookRequest>()
            call.respond(
                HttpStatusCode.Created,
                createBookUseCase.execute(
                    segmentationRequest.toCreateBookCommand()
                )
            )
        }
    }
}
