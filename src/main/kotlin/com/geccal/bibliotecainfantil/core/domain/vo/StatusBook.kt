package com.geccal.bibliotecainfantil.core.domain.vo

import com.geccal.bibliotecainfantil.core.domain.exception.NotificationException
import com.geccal.bibliotecainfantil.core.domain.validator.CustomError
import com.geccal.bibliotecainfantil.core.domain.validator.ValidationHandler
import com.geccal.bibliotecainfantil.core.domain.validator.handler.NotificationHandler

enum class StatusBook {
    AVAILABLE,
    BORROWED,
    LOSS,
    INAPPROPRIATE,
    MISPLACED,
    DONATED;

    companion object {
        fun create(value: String): StatusBook {
            selfValidate(value)
            return StatusBook.valueOf(value)
        }
        private fun selfValidate(value: String) {
            val notification = NotificationHandler.create()
            validate(value, notification)
            if (notification.hasError) {
                throw NotificationException("Failed to create a Value Object StatusBook", notification)
            }
        }

        private fun validate(value: String, validationHandler: ValidationHandler) {
            if (value.isEmpty()) {
                validationHandler.append(CustomError("status: Is empty"))
            } else {
                runCatching {
                    StatusBook.valueOf(value)
                }.onFailure {
                    validationHandler.append(CustomError("status: Is invalid"))
                }
            }
        }
    }
}
