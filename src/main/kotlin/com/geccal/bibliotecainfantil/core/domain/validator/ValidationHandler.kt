package com.geccal.bibliotecainfantil.core.domain.validator

interface ValidationHandler {
    fun append(error: CustomError): ValidationHandler
    fun append(error: ValidationHandler): ValidationHandler
    fun getErrors(): List<CustomError>
    val hasError get(): Boolean = getErrors().isNotEmpty()

    interface Validation {
        fun validate()
    }

    fun <T> validate(validate: () -> T): T?
}
