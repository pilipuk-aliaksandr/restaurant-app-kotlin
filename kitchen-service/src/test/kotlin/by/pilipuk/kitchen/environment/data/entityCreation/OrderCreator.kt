package by.pilipuk.kitchen.environment.data.entityCreation

import by.pilipuk.kitchen.business.repository.OrderRepository
import by.pilipuk.kitchen.model.entity.Order
import by.pilipuk.kitchen.model.enums.Status
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class OrderCreator(
    private val orderRepository: OrderRepository
) {

    fun createOrder(): Order = Order().apply {
        id = 1L
        orderId = 1L
        status = Status.ACCEPTED
        active = true
        createdAt = LocalDateTime.now()
    }

    fun saveOrder(order: Order): Order {
        order.id = null
        return orderRepository.save(order)
    }
}