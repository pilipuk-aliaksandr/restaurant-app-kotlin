package by.pilipuk.order.exception

import org.slf4j.event.Level

enum class ApplicationExceptionCode(val level: Level, val key: String) {
    NOT_FOUND_BY_ID(Level.INFO, "id")
}