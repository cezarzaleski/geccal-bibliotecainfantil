package com.geccal.bibliotecainfantil.core.application.exception

import com.geccal.bibliotecainfantil.core.domain.exception.DomainException
import com.geccal.bibliotecainfantil.core.domain.validator.CustomError

open class ValidationException constructor(
    message: String,
    errors: List<CustomError>
) : DomainException(message, errors)
