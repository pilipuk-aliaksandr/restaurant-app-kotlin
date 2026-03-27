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
    private val outboxEventRepository: OutboxEventRepository,
    private val kafkaTemplate: KafkaTemplate<String, Any>
) {
    private val log = KotlinLogging.logger {}

    @Transactional
    fun sendOutboxEvent(): Boolean =
        outboxEventRepository.findNextForProcessing()?.let {
            return try {
                kafkaTemplate.send(it.topic,it.orderId,it.payload).get()
                it.active = false
                outboxEventRepository.save(it)
                true
            } catch (e: Exception) {
                log.error( e) {"Failed to send event: ${it.orderId}, ${e.message}"}
                throw ApplicationException.create(
                    ApplicationExceptionCode.FAILED_MESSAGING_TO_KAFKA, e)
            }
        } ?: false
}