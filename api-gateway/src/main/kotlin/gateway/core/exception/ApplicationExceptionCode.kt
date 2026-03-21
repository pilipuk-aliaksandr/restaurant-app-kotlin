package by.pilipuk.gateway.core.exception

import io.github.oshai.kotlinlogging.Level

enum class ApplicationExceptionCode (val level: Level, val key: String) {
    NOT_FOUND_BY_ID(Level.INFO, "id"),
    NOT_FOUND_BY_USERNAME(Level.INFO, "username")
}