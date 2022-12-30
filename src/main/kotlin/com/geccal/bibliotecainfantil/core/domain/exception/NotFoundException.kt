package com.geccal.bibliotecainfantil.core.domain.exception

import com.geccal.bibliotecainfantil.core.domain.Identifier
import com.geccal.bibliotecainfantil.core.domain.validator.CustomError

open class NotFoundException protected constructor(
    message: String,
    errors: List<CustomError>
) : DomainException(message, errors) {
    companion object {
        fun from(aggregate: String, id: Identifier): NotFoundException {
            val error = "$aggregate with identifier ${id.value}"
            return NotFoundException(error, emptyList())
        }
    }
}
