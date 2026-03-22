package by.pilipuk.commonKafka.business.service

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
class OutboxEventSchedulerService(val eventSender: EventSender) {

    @Scheduled(fixedDelay = 5000)
    fun processOutbox() {
        repeat(50) {
            runCatching {
                eventSender.sendOutboxEvent()
            }.getOrElse {
                return
            }.let { processed ->
                if (!processed) return
            }
        }
    }
}