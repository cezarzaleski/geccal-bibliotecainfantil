package com.geccal.bibliotecainfantil.core.domain.validator

import com.geccal.bibliotecainfantil.core.domain.vo.Author

class AuthorValidator(private val author: Author, handler: ValidationHandler) : Validator(handler) {
    override fun validate() {
        if (author.value.isEmpty()) validationHandler.append(CustomError("author: Is empty"))
    }
}
