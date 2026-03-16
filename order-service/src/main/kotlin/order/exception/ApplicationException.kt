package by.pilipuk.order.exception

import by.pilipuk.commonCore.core.exception.base.BaseApplicationException
import by.pilipuk.commonCore.model.dto.ExceptionContext
import org.slf4j.event.Level

class ApplicationException private constructor(
    logLevel: Level,
    exceptionContext: ExceptionContext,
    code: String = CODE
) : BaseApplicationException(code, logLevel, exceptionContext) {

    companion object {
        private const val CODE = "ORDER_APPLICATION_EXCEPTION"

        fun create(code: ApplicationExceptionCode, param: Any): ApplicationException {
            val context = ExceptionContext.create(code.name)
                .add(code.key, param)

            return ApplicationException(code.level, context)
        }
    }
}