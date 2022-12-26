package com.geccal.bibliotecainfantil.core.domain.exception

import com.geccal.bibliotecainfantil.core.domain.validator.CustomError

open class DomainException constructor(aMessage: String?, val errors: List<CustomError>): Exception(aMessage) {

    companion object {
        fun with(anErrors: CustomError): DomainException {
            return DomainException(anErrors.message, listOf(anErrors))
        }

        fun with(anErrors: List<CustomError>): DomainException {
            return DomainException("", anErrors)
        }
    }
}