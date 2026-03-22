package by.pilipuk.commonCore.entrypoint

import by.pilipuk.commonCore.core.exception.base.BaseApplicationException
import by.pilipuk.commonCore.core.mapper.ExceptionMapper.toDto
import by.pilipuk.commonCore.model.dto.ExceptionDto
import io.github.oshai.kotlinlogging.KotlinLogging
import io.github.oshai.kotlinlogging.Level
import org.springframework.http.HttpStatus
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {
    private val log = KotlinLogging.logger {}

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BaseApplicationException::class)
    fun handleBaseApplicationException(ex: BaseApplicationException): ExceptionDto {
        ex.logByLevel()
        return ex.toDto()
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValid(ex: MethodArgumentNotValidException): ExceptionDto {
        log.debug(ex) { "Validation failed: ${ex.message}" }
        return ex.toDto()
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception::class)
    fun handleOtherApplicationException(ex: Exception): ExceptionDto {
        log.error(ex) { "Unexpected error: ${ex.message}" }
        return ex.toDto()
    }

    private fun BaseApplicationException.logByLevel() {
        when (this.logLevel) {
            Level.ERROR -> log.error(this) { "[ERROR] $message" }
            Level.DEBUG -> log.debug { "[DEBUG] $message" }
            else -> log.info { "[INFO] $message" }
        }
    }
}