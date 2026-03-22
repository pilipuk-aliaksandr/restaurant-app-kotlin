package by.pilipuk.kitchen.core.exception

import by.pilipuk.commonCore.core.exception.base.BaseApplicationException
import by.pilipuk.commonCore.model.dto.ExceptionContext
import io.github.oshai.kotlinlogging.Level

class ApplicationException private constructor(
    logLevel: Level,
    exceptionContext: ExceptionContext,
    code: String
) : BaseApplicationException(code, logLevel, exceptionContext) {

    companion object {
        private const val CODE = "KITCHEN_APPLICATION_EXCEPTION"

        fun create(code: ApplicationExceptionCode, param: Any): ApplicationException {
            val context = ExceptionContext.create(code.name)
                .add(code.key, param)

            return ApplicationException(code.level, context, CODE)
        }
    }
}