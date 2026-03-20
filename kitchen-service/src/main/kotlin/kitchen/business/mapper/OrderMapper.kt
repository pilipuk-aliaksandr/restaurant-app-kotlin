package by.pilipuk.kitchen.business.mapper

import by.pilipuk.commonCore.core.util.toJson
import by.pilipuk.kitchen.dto.OrderDto
import by.pilipuk.kitchen.dto.OrderItemsDto
import by.pilipuk.kitchen.model.entity.Order
import by.pilipuk.kitchen.model.entity.OrderItem
import by.pilipuk.kitchen.model.enums.Status
import by.pilipuk.commonKafka.model.dto.OrderCreatedEvent
import by.pilipuk.commonKafka.model.dto.OrderReadyEvent
import by.pilipuk.commonKafka.model.entity.OutboxEvent

object OrderMapper {

    fun Order.toDto() = OrderDto(
        id = this.id,
        orderId = this.orderId,
        status = this.status.name,
        items = this.items.map { orderItem ->
            OrderItemsDto(
                id = orderItem.id,
                itemName = orderItem.name,
                cooked = orderItem.cooked,
            )
        }
    )

    fun Order.toReadyEvent() = OrderReadyEvent().apply {
        orderId = this@toReadyEvent.id
        items = this@toReadyEvent.items.map { it -> it.name }
    }

    fun Order.toOutboxEvent() = OutboxEvent().apply {
        topic = "ready_orders"
        orderId = this@toOutboxEvent.id.toString()
        payload = this@toOutboxEvent.toReadyEvent().toJson()
    }

    fun OrderCreatedEvent.toEntityFromEvent() = Order().apply {
        val currentOrder = this
        orderId = this@toEntityFromEvent.orderId
        status = Status.ACCEPTED
        items = this@toEntityFromEvent.items.map { itemName ->
            OrderItem().apply {
                name = itemName
                order = currentOrder
            }
        }.toMutableList()
    }
}