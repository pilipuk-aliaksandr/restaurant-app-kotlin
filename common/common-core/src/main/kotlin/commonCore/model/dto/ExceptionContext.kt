package by.pilipuk.commonCore.model.dto

import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
//@JsonPropertyOrder("code", "field", "message", "expected", "actual", "object", "parameters")
data class ExceptionContext(
    var code: String? = null,
    var message: String? = null,
    var field: String? = null,
    var actual: Any? = null,
    var expected: Any? = null,
    var `object`: String? = null,
    var parameters: MutableMap<String, Any>? = null
) {
    companion object {
        fun create(code: String) = ExceptionContext(code = code)
    }

    fun add(key: String, value: Any?): ExceptionContext = apply {
        parameters?.put(key, value ?: "null")
    }
}