package com.geccal.bibliotecainfantil.boundaries.api

import io.ktor.server.application.call
import io.ktor.server.response.respondText
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get
import io.ktor.server.routing.route

fun Routing.configureRouting() {

    // Starting point for a Ktor app:
    route("/") {
        get {
            call.respondText("Hello World!")
        }
    }
}
