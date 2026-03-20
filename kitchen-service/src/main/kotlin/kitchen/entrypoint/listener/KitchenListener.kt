package by.pilipuk.kitchen.entrypoint.listener

import by.pilipuk.kitchen.business.service.OrderService
import by.pilipuk.commonKafka.model.dto.OrderCreatedEvent
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class KitchenListener(val orderService: OrderService) {

    @KafkaListener(topics = ["orders"], groupId = "kitchen-group")
    fun listen(orderCreatedEvent: OrderCreatedEvent) {
            orderService.acceptNewOrder(orderCreatedEvent)
    }



}