package by.pilipuk.order.business.mapper

import by.pilipuk.commonCore.core.util.toJson
import by.pilipuk.order.dto.OrderDto
import by.pilipuk.order.dto.OrderItemsDto
import by.pilipuk.order.dto.OrderWriteDto
import by.pilipuk.order.model.entity.Order
import by.pilipuk.order.model.entity.OrderItem
import by.pilipuk.order.model.enum.Status
import by.pilipuk.commonKafka.model.dto.OrderCreatedEvent
import by.pilipuk.commonKafka.model.entity.OutboxEvent

object OrderMapper {
    fun OrderWriteDto.toEntity() = Order().apply {
        val currentOrder = this
        tableNumber = this@toEntity.tableNumber
        status = Status.CREATED
        items = this@toEntity.items.map { orderItemsWriteDto ->
            OrderItem().apply {
                name = orderItemsWriteDto.itemName
                order = currentOrder
            }
        }.toMutableList()
    }

    fun Order.toDto() = OrderDto(
        id = this.id,
        tableNumber = this.tableNumber,
        status = this.status.name,
        items = this.items.map { orderItem ->
            OrderItemsDto(
                id = orderItem.id,
                itemName = orderItem.name,
            )
        }
    )

    fun Order.toCreatedEvent() = OrderCreatedEvent(
        orderId = this.id,
        items = this.items.map { orderItem -> orderItem.name }
    )

    fun Order.toOutboxEvent() = OutboxEvent().apply {
        topic = "orders"
        orderId = this@toOutboxEvent.id.toString()
        payload = this@toOutboxEvent.toCreatedEvent().toJson()
    }
}