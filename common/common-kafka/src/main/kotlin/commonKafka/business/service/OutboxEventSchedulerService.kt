package by.pilipuk.commonKafka.business.service

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service

@Service
class OutboxEventSchedulerService(private val eventSender: EventSender) {

    @Scheduled(fixedDelay = 5000)
    fun processOutbox() {
        repeat(50) {
            runCatching { eventSender.sendOutboxEvent() }
                .getOrNull()
                takeIf { true }
                ?: return@repeat
        }
    }
}