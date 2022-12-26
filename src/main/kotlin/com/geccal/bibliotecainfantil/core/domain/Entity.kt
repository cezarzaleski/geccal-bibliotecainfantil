package com.geccal.bibliotecainfantil.core.domain

import com.geccal.bibliotecainfantil.core.domain.validator.ValidationHandler

abstract class Entity<ID : Identifier> protected constructor(open val id: ID) {
    abstract fun validate(handler: ValidationHandler)
}
