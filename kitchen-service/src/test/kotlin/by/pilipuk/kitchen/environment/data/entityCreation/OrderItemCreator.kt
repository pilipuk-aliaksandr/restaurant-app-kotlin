package by.pilipuk.kitchen.environment.data.entityCreation

import by.pilipuk.kitchen.business.repository.OrderItemRepository
import by.pilipuk.kitchen.model.entity.Order
import by.pilipuk.kitchen.model.entity.OrderItem
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class OrderItemCreator(
    private val orderItemRepository: OrderItemRepository
) {

    private fun createOrderItem(id: Long, order: Order, name: String): OrderItem =
        OrderItem().apply {
            this.id = id
            this.order = order
            this.name = name
            cooked = false
            active = true
            createdAt = LocalDateTime.now()
        }

    fun createOrderItems(order: Order): List<OrderItem> = listOf(
        createOrderItem(1L, order, "Pizza"),
        createOrderItem(2L, order, "Coca-Cola")
    )

    private fun saveOrderItem(orderItem: OrderItem): OrderItem {
        orderItem.id = null
        return orderItemRepository.save(orderItem)
    }

    fun saveOrderItems(order: Order): List<OrderItem> = listOf(
        saveOrderItem(createOrderItem(1L, order, "Pizza")),
        saveOrderItem(createOrderItem(2L, order, "Coca-Cola"))
    )
}