package by.pilipuk.commonCore.core.util

import tools.jackson.core.type.TypeReference
import tools.jackson.databind.ObjectMapper
import tools.jackson.module.kotlin.jacksonObjectMapper
import tools.jackson.module.kotlin.readValue

val sharedMapper: ObjectMapper = jacksonObjectMapper()

fun Any?.toJson(): String = try {
    sharedMapper.writeValueAsString(this)
} catch (e: Exception) {
    throw RuntimeException("Failed to serialize object to JSON", e)
}

inline fun <reified T> String.toObject(): T = try {
    sharedMapper.readValue(this)
} catch (e: Exception) {
    throw RuntimeException("Failed to deserialize JSON to ${T::class.java.simpleName}", e)
}

fun <T> String.toObject(typeReference: TypeReference<T>): T = try {
    sharedMapper.readValue(this, typeReference)
} catch (e: Exception) {
    throw RuntimeException("Failed to deserialize JSON with TypeReference", e)
}