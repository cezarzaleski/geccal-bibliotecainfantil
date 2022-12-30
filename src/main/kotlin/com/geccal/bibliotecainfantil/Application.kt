package com.geccal.bibliotecainfantil

import com.geccal.bibliotecainfantil.boundaries.api.book.booksRouter
import com.geccal.bibliotecainfantil.boundaries.api.configureRouting
import com.geccal.bibliotecainfantil.infra.configuration.Usecases
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpMethod
import io.ktor.server.application.Application
import io.ktor.server.application.install
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.plugins.autohead.AutoHeadResponse
import io.ktor.server.plugins.callloging.CallLogging
import io.ktor.server.plugins.cors.routing.CORS
import io.ktor.server.request.path
import io.ktor.server.routing.Routing
import org.slf4j.event.Level

fun main() {
    embeddedServer(
        Netty,
        port = 8080,
        host = "0.0.0.0",
        module = {
            bibliotecaInfantil {
                booksRouter(Usecases.createBookUseCase)
                configureRouting()
            }
        })
        .start(wait = true)
}

fun Application.bibliotecaInfantil(route: Routing.() -> Unit = {}) {
    install(CallLogging) {
        level = Level.INFO
        filter { call -> call.request.path().startsWith("/") }
    }

    install(CORS) {
        allowMethod(HttpMethod.Options)
        allowMethod(HttpMethod.Put)
        allowMethod(HttpMethod.Delete)
        allowMethod(HttpMethod.Patch)
        allowHeader(HttpHeaders.Authorization)
        allowHeader("MyCustomHeader")
        anyHost() // @TODO: Don't do this in production if possible. Try to limit it.
    }

    install(Routing) {
        route()
    }

    install(AutoHeadResponse)
//    install(ContentNegotiation) {
//        jackson {
//            register(ContentType.Application.Json, JacksonConverter(JsonMapper.mapper))
//        }
//    }
}

