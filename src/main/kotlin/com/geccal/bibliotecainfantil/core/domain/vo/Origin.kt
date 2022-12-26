package com.geccal.bibliotecainfantil.core.domain.vo

import com.geccal.bibliotecainfantil.core.domain.exception.NotificationException
import com.geccal.bibliotecainfantil.core.domain.validator.CustomError
import com.geccal.bibliotecainfantil.core.domain.validator.ValidationHandler
import com.geccal.bibliotecainfantil.core.domain.validator.handler.NotificationHandler

enum class Origin {
    ACQUISITION,
    CONFECTION,
    DONATION,
    NO_INFORMATION,
    UNKNOWN;

    companion object {
        fun create(value: String): Origin {
            selfValidate(value)
            return Origin.valueOf(value)
        }
        private fun selfValidate(value: String) {
            val notification = NotificationHandler.create()
            validate(value, notification)
            if (notification.hasError) {
                throw NotificationException("Failed to create a Value Object Origin", notification)
            }
        }

        private fun validate(value: String, validationHandler: ValidationHandler) {
            if (value.isEmpty()) validationHandler.append(CustomError("origin: Is empty"))
            runCatching {
                Origin.valueOf(value)
            }.onFailure {
                validationHandler.append(CustomError("origin: Is invalid"))
            }
        }
    }
}