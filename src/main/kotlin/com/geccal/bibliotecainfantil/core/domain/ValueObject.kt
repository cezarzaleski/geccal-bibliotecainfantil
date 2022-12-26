package com.geccal.bibliotecainfantil.core.domain

import com.geccal.bibliotecainfantil.core.domain.validator.ValidationHandler

abstract class ValueObject {
    abstract fun validate(handler: ValidationHandler)
}
