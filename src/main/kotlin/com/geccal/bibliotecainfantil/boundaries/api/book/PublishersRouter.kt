package com.geccal.bibliotecainfantil.boundaries.api.book

import com.geccal.bibliotecainfantil.boundaries.api.model.extension.toSearchQuery
import com.geccal.bibliotecainfantil.core.application.book.retrieve.list.ListPublisherUseCase
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get
import io.ktor.server.routing.route

fun Routing.publishersRouter(
    listPublisherUseCase: ListPublisherUseCase
) {
    route("/publishers") {
        get {
            call.respond(
                HttpStatusCode.OK,
                listPublisherUseCase.execute(call.request.toSearchQuery("publisher"))
            )
        }
    }
}
