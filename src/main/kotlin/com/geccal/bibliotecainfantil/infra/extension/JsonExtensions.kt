package com.geccal.bibliotecainfantil.infra.extension

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule

fun <T : Any> T.toJson(): String = JsonMapper.mapper.writeValueAsString(this)

fun readFromAnyToMap(value: Any): Map<String, Any> {
    val typeRef: TypeReference<Map<String, Any>> = object : TypeReference<Map<String, Any>>() {}
    if (value is LinkedHashMap<*, *>) return value as Map<String, Any>
    return jacksonObjectMapper().readValue(value.toString(), typeRef)
}

fun <T> readValue(value: String): T {
    val typeRef: TypeReference<T> = object : TypeReference<T>() {}
    return jacksonObjectMapper().readValue(value, typeRef)
}

object JsonMapper {
    val mapper: ObjectMapper = with(jacksonObjectMapper()) {
        configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
        configure(SerializationFeature.INDENT_OUTPUT, false)
        configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true)
        registerModule(JavaTimeModule())
        setSerializationInclusion(JsonInclude.Include.ALWAYS)
    }
}
