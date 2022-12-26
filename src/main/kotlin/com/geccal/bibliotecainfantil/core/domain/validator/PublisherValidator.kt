package com.geccal.bibliotecainfantil.core.domain.validator

import com.geccal.bibliotecainfantil.core.domain.vo.Publisher

class PublisherValidator(private val publisher: Publisher, handler: ValidationHandler) : Validator(handler) {
    override fun validate() {
        if (publisher.value.isEmpty()) validationHandler.append(CustomError("publisher: Is empty"))
    }
}