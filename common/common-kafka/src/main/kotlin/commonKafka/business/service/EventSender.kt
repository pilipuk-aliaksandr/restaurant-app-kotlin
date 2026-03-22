package by.pilipuk.commonKafka.business.service

import by.pilipuk.commonKafka.business.repository.OutboxEventRepository
import by.pilipuk.commonKafka.core.exception.ApplicationException
import by.pilipuk.commonKafka.core.exception.ApplicationExceptionCode
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class EventSender(
    val outboxEventRepository: OutboxEventRepository,
    val kafkaTemplate: KafkaTemplate<String, Any>
) {
    @Transactional
    fun sendOutboxEvent(): Boolean {
        val event = outboxEventRepository.findNextForProcessing() ?: return false
        val log = KotlinLogging.logger {}

        return try {
            kafkaTemplate.send(event.topic,event.orderId,event.payload).get()

            event.active = false
            outboxEventRepository.save(event)

            true
        } catch (e: Exception) {
            log.error( e) {"Failed to send event: ${event.orderId}, ${e.message}"}
            throw ApplicationException.create(
                ApplicationExceptionCode.FAILED_MESSAGING_TO_KAFKA, e)
        }
    }
}