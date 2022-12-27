package com.geccal.bibliotecainfantil.infra.database

interface Connection {

    suspend fun <T> query(statement: String, params: Map<String, Any>?): T?
    suspend fun <T> persist(statement: String, params: Map<String, Any?>?)
}
