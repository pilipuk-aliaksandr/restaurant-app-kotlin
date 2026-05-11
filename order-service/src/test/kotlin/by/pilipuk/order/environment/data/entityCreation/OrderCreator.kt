package by.pilipuk.order.environment.data.entityCreation

import by.pilipuk.order.business.repository.OrderRepository
import by.pilipuk.order.model.entity.Order
import by.pilipuk.order.model.enum.Status
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Component
class OrderCreator(
    private val orderRepository: OrderRepository
) {

    fun createOrder(): Order = Order().apply {
        id = 1L
        tableNumber = 1
        status = Status.CREATED
        active = true
        createdAt = LocalDateTime.now()
    }

    fun createOrderWithStatusReady(): Order = Order().apply {
        id = 1L
        tableNumber = 1
        status = Status.READY
        active = true
        createdAt = LocalDateTime.now()
    }

    @Transactional
    fun saveTestOrderToDB(order: Order): Order {
        order.id = null
        return orderRepository.save(order)
    }
}