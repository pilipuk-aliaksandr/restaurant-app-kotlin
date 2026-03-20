package by.pilipuk.order.entrypoint.listener

import by.pilipuk.order.business.service.OrderService
import by.pilipuk.commonKafka.model.dto.OrderReadyEvent
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class OrderListener(val orderService: OrderService) {

    @KafkaListener(topics = ["ready_orders"], groupId = "orders-group")
    fun listen(orderReadyEvent: by.pilipuk.commonKafka.model.dto.OrderReadyEvent) {
        orderService.acceptCookedOrders(orderReadyEvent)
    }
}