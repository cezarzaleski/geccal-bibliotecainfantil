package com.geccal.bibliotecainfantil.core.domain.validator.handler

import com.geccal.bibliotecainfantil.core.domain.exception.DomainException
import com.geccal.bibliotecainfantil.core.domain.validator.CustomError
import com.geccal.bibliotecainfantil.core.domain.validator.ValidationHandler

class NotificationHandler(private val errors: MutableList<CustomError> = mutableListOf()) :
    ValidationHandler {
    override fun append(error: CustomError): NotificationHandler {
        errors.add(error)
        return this
    }

    override fun append(error: ValidationHandler): NotificationHandler {
        errors.addAll(error.getErrors())
        return this
    }

    override fun getErrors(): List<CustomError> = errors
    override fun <T> validate(validate: () -> T): T? {
        runCatching {
            validate()
        }.onFailure {
            when (it) {
                is DomainException -> {
                    errors.addAll(it.errors)
                }

                else -> it.message?.let { message -> CustomError(message) }?.let { message -> errors.add(message) }
            }
        }
        return null
    }

    operator fun ValidationHandler.invoke(validation: ValidationHandler.Validation): ValidationHandler {
        runCatching {
            validation.validate()
        }.onFailure {
            when (it) {
                is DomainException -> {
                    errors.addAll(it.errors)
                }

                else -> it.message?.let { message -> CustomError(message) }?.let { message -> errors.add(message) }
            }
        }
        return this
    }

    companion object {
        fun create(t: Throwable): NotificationHandler {
            return t.message?.let { create(CustomError(it)) } ?: create()
        }

        fun create(): NotificationHandler = NotificationHandler()
        fun create(customError: CustomError): NotificationHandler = NotificationHandler().append(customError)
    }
}
