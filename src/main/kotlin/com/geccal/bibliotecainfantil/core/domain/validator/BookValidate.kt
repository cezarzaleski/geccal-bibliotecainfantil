package com.geccal.bibliotecainfantil.core.domain.validator

import com.geccal.bibliotecainfantil.core.domain.entity.Book

class BookValidate(private val book: Book, handler: ValidationHandler) : Validator(handler) {
    override fun validate() {
        if (book.name.isEmpty()) validationHandler.append(CustomError("name: Is empty"))
        if (book.exemplary <= 0) validationHandler.append(CustomError("exemplary: Is invalid"))
        if (book.edition.isEmpty()) validationHandler.append(CustomError("edition: Is empty"))
    }
}