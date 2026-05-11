package by.pilipuk.kitchen.environment.data.eventCreation

import by.pilipuk.commonKafka.model.dto.OrderCreatedEvent
import org.springframework.stereotype.Component

@Component
class EventCreator {

    fun createOrderCreatedEvent(): OrderCreatedEvent = OrderCreatedEvent(
        orderId = 1L,
        items = listOf("Pizza", "Coca-Cola")
    )
}