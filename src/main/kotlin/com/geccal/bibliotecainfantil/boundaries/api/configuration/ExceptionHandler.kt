package com.geccal.bibliotecainfantil.boundaries.api.configuration

import com.geccal.bibliotecainfantil.core.application.exception.ValidationException
import com.geccal.bibliotecainfantil.core.domain.validator.CustomError
import io.ktor.http.HttpStatusCode
import io.ktor.server.plugins.BadRequestException
import io.ktor.server.plugins.statuspages.StatusPagesConfig
import io.ktor.server.response.respond

fun StatusPagesConfig.statusPages() {
    exception<ValidationException> { call, cause ->
        val httpStatus = getExceptionCode(cause)
        val exceptionMessage = cause.message

        call.respond(httpStatus, ErrorResponse(exceptionMessage.orEmpty(), cause.errors))
    }
}

private fun getExceptionCode(exception: Throwable): HttpStatusCode {
    return when (exception) {
        is ValidationException -> HttpStatusCode.BadRequest
        is BadRequestException -> HttpStatusCode.BadRequest
        else -> HttpStatusCode.InternalServerError
    }
}

data class ErrorResponse(val message: String, val details: List<CustomError>)
