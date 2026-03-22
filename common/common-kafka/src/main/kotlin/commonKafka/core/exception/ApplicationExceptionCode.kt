package by.pilipuk.commonKafka.core.exception

import io.github.oshai.kotlinlogging.Level

enum class ApplicationExceptionCode(val level: Level, val key: String) {
    FAILED_MESSAGING_TO_KAFKA(Level.INFO, "id")
}