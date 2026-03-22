package by.pilipuk.commonCore.core.mapper

import by.pilipuk.commonCore.core.exception.base.BaseApplicationException
import by.pilipuk.commonCore.model.dto.ExceptionContext
import by.pilipuk.commonCore.model.dto.ExceptionDto
import org.springframework.web.bind.MethodArgumentNotValidException

object ExceptionMapper {
    fun BaseApplicationException.toDto() = ExceptionDto(
        code = this.code,
        rootCause = this.cause?.message,
        contexts = listOf(exceptionContext)
    )

    fun MethodArgumentNotValidException.toDto() = ExceptionDto(
        code = "VALIDATION_ERROR",
        contexts = bindingResult.fieldErrors.map { error ->
            ExceptionContext.create("INVALID_FIELD").apply {
                field = error.field
                message = error.defaultMessage
                actual = error.rejectedValue
            }
        }
    )

    fun Exception.toDto() = ExceptionDto(
        code = "INTERNAL_SERVER_ERROR",
        rootCause = this.message
    )
}