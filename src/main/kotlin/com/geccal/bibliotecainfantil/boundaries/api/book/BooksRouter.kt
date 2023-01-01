package com.geccal.bibliotecainfantil.boundaries.api.book

import com.geccal.bibliotecainfantil.boundaries.api.book.models.CreateBookRequest
import com.geccal.bibliotecainfantil.core.application.book.create.CreateBookUseCase
import com.geccal.bibliotecainfantil.core.application.book.retrieve.list.ListBookUseCase
import com.geccal.bibliotecainfantil.core.domain.pagination.Pagination
import com.geccal.bibliotecainfantil.core.domain.pagination.SearchQuery
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
            val search = call.request.queryParameters["search"].orEmpty()
            val page = call.request.queryParameters["page"]?.toIntOrNull() ?: Pagination.DEFAULT_PAGE
            val perPage = call.request.queryParameters["perPage"]?.toIntOrNull() ?: Pagination.DEFAULT_PER_PAGE
            val sort = call.request.queryParameters["sort"].orEmpty()
            val direction = call.request.queryParameters["direction"] ?: "name"
            val searchQuery = SearchQuery(page, perPage, search, sort, direction)

            call.respond(
                HttpStatusCode.OK,
                listBookUseCase.execute(searchQuery)
            )
        }
    }
}
