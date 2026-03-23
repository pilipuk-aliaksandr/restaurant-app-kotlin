package by.pilipuk.commonCore.core.util

import tools.jackson.databind.ObjectMapper
import tools.jackson.module.kotlin.jacksonObjectMapper

object Json {
    val sharedMapper: ObjectMapper = jacksonObjectMapper()

    fun Any?.toJson(): String = try {
        sharedMapper.writeValueAsString(this)
    } catch (e: Exception) {
        throw RuntimeException("Failed to serialize object to JSON", e)
    }
}