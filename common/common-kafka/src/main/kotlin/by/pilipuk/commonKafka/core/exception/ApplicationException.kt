package by.pilipuk.commonKafka.core.exception

import by.pilipuk.commonCore.core.exception.base.BaseApplicationException
import by.pilipuk.commonCore.model.dto.ExceptionContext
import io.github.oshai.kotlinlogging.Level

class ApplicationException private constructor(
    logLevel: Level,
    exceptionContext: ExceptionContext,
    code: String
) : BaseApplicationException(code, logLevel, exceptionContext) {

    companion object {
        private const val CODE = "COMMON-KAFKA_APPLICATION_EXCEPTION"

        fun create(code: by.pilipuk.commonKafka.core.exception.ApplicationExceptionCode, param: Any): ApplicationException {
            val context = ExceptionContext.create(code.name)
                .add(code.key, param)

            return ApplicationException(code.level, context, CODE)
        }
    }
}