package by.pilipuk.kitchen.core.exception

import io.github.oshai.kotlinlogging.Level

enum class ApplicationExceptionCode (val level: Level, val key: String) {
    NOT_FOUND_BY_ID(Level.INFO, "id")
}