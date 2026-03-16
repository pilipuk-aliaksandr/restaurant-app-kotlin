package by.pilipuk.commonCore.core.exception.base

import by.pilipuk.commonCore.model.dto.ExceptionContext
import org.slf4j.event.Level

abstract class BaseApplicationException (
    val code: String,
    val logLevel: Level,
    val exceptionContext: ExceptionContext,
    cause: Throwable? = null
) : RuntimeException(createSuperMessage(exceptionContext), cause) {

    companion object {
        private fun createSuperMessage(context: ExceptionContext): String {
            val params = context.parameters
            return if (!params.isNullOrEmpty()) {
                "Code: ${context.code}, params: [$params]"
            } else {
                "Code: ${context.code}"
            }
        }
    }
}