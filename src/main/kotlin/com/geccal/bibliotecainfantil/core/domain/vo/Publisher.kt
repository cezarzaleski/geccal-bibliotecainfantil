package com.geccal.bibliotecainfantil.core.domain.vo

import com.geccal.bibliotecainfantil.core.domain.ValueObject
import com.geccal.bibliotecainfantil.core.domain.validator.PublisherValidator
import com.geccal.bibliotecainfantil.core.domain.validator.ValidationHandler

data class Publisher private constructor(
    val value: String
) : ValueObject() {
    companion object {
        fun create(
            value: String
        ): Publisher {
            return Publisher(value)
        }
    }

    override fun validate(handler: ValidationHandler) {
        PublisherValidator(this, handler).validate()
    }
}
