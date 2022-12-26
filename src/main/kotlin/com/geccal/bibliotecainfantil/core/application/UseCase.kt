package com.geccal.bibliotecainfantil.core.application

interface UseCase<IN, OUT> {
    fun execute(input: IN): OUT
}
