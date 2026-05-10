package by.pilipuk.order.environment.data.eventCreation

import by.pilipuk.commonKafka.model.dto.OrderCreatedEvent
import by.pilipuk.commonKafka.model.dto.OrderReadyEvent
import org.springframework.stereotype.Component

@Component
class EventCreator {
    fun createOrderCreatedEvent(): OrderCreatedEvent {
        return OrderCreatedEvent(
            orderId = 1L,
            items = mutableListOf<String>("Pizza", "Coca-Cola")
        )
    }

    fun createOrderReadyEvent(): OrderReadyEvent {
        return OrderReadyEvent(
            orderId = 1L,
            items = mutableListOf<String>("Pizza", "Coca-Cola")
        )
    }
}