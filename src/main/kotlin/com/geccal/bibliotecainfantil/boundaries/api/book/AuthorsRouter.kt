package com.geccal.bibliotecainfantil.boundaries.api.book

import com.geccal.bibliotecainfantil.boundaries.api.model.extension.toSearchQuery
import com.geccal.bibliotecainfantil.core.application.book.retrieve.list.ListAuthorUseCase
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get
import io.ktor.server.routing.route

fun Routing.authorsRouter(
    listAuthorUseCase: ListAuthorUseCase
) {
    route("/authors") {
        get {
            call.respond(
                HttpStatusCode.OK,
                listAuthorUseCase.execute(call.request.toSearchQuery("author"))
            )
        }
    }
}
