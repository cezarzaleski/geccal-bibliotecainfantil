package com.geccal.bibliotecainfantil.core.domain.vo

import com.geccal.bibliotecainfantil.core.domain.ValueObject
import com.geccal.bibliotecainfantil.core.domain.validator.AuthorValidator
import com.geccal.bibliotecainfantil.core.domain.validator.ValidationHandler

data class Author private constructor(
    val value: String
) : ValueObject() {
    companion object {
        fun create(
            value: String
        ): Author {
            return Author(value)
        }
    }

    override fun validate(handler: ValidationHandler) {
        AuthorValidator(this, handler).validate()
    }
}