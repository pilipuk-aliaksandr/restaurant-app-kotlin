package by.pilipuk.order.environment.data.entityCreation

import by.pilipuk.order.business.repository.OrderItemRepository
import by.pilipuk.order.model.entity.Order
import by.pilipuk.order.model.entity.OrderItem
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Component
class OrderItemsCreator(
    private val orderItemRepository: OrderItemRepository
) {

    private fun createOrderItem(id: Long, order: Order, name: String): OrderItem =
        OrderItem().apply {
            this.id = id
            this.order = order
            this.name = name
            active = true
            createdAt = LocalDateTime.now()
        }

    fun createOrderItems(order: Order): List<OrderItem> = listOf(
        createOrderItem(1L, order, "Pizza"),
        createOrderItem(2L, order, "Coca-Cola")
    )

    @Transactional
    fun saveTestOrderItemToDB(orderItem: OrderItem): OrderItem {
        orderItem.id = null
        return orderItemRepository.save(orderItem)
    }

    @Transactional
    fun saveTestOrderItemsToDB(order: Order): List<OrderItem> = listOf(
        saveTestOrderItemToDB(createOrderItem(1L, order, "Pizza")),
        saveTestOrderItemToDB(createOrderItem(2L, order, "Coca-Cola"))
    )
}