package com.geccal.bibliotecainfantil.core.domain

import com.geccal.bibliotecainfantil.core.domain.validator.ValidationHandler

abstract class AggregateRoot<ID : Identifier> protected constructor(override val id: ID) : Entity<ID>(id) {
    abstract override fun validate(handler: ValidationHandler)
}
