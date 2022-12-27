package com.geccal.bibliotecainfantil.core.application

interface UseCase<IN, OUT> {
    suspend fun execute(input: IN): OUT
}
