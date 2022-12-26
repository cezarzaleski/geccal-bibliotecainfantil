package com.geccal.bibliotecainfantil.core.domain.validator

abstract class Validator(private val handler: ValidationHandler) {
    abstract fun validate()
    protected val validationHandler get() = this.handler
}
