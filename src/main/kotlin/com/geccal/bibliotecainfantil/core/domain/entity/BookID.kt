package com.geccal.bibliotecainfantil.core.domain.entity

import com.geccal.bibliotecainfantil.core.domain.Identifier
import com.geccal.bibliotecainfantil.core.domain.validator.CustomError
import com.geccal.bibliotecainfantil.core.domain.validator.ValidationHandler
import java.util.Locale
import java.util.UUID

class BookID private constructor(override val value: String) : Identifier() {
    companion object {
        fun unique(): BookID = from(UUID.randomUUID())
        fun from(id: String): BookID = BookID(id)
        fun from(id: UUID): BookID = BookID(id.toString().lowercase(Locale.getDefault()))
    }

    override fun validate(handler: ValidationHandler) {
        runCatching {
            UUID.fromString(value)
        }.onFailure {
            when (it) {
                is IllegalArgumentException -> {
                    handler.append(CustomError("BookID: $value invalid"))
                }
                else -> throw it
            }
        }
    }
}
