package by.pilipuk.commonCore.model.dto

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ExceptionDto(
    val code: String? = null,
    val contexts: List<ExceptionContext>? = null,
    val rootCause: String? = null
)