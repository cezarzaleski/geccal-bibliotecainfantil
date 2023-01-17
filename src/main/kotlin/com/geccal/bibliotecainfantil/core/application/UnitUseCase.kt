package com.geccal.bibliotecainfantil.core.application

interface UnitUseCase<IN> {
    suspend fun execute(input: IN)
}
