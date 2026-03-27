package by.pilipuk.kitchen.business.mapper

import by.pilipuk.kitchen.dto.OrderItemsDto
import by.pilipuk.kitchen.model.entity.Order
import by.pilipuk.kitchen.model.entity.OrderItem

fun OrderItem.toDto() = OrderItemsDto(
    id = this.id,
    orderId = this.id,
    itemName = this.name,
    cooked = this.cooked,
)

fun String.toEntity(currentOrder: Order) = OrderItem().apply {
    name = this@toEntity
    order = currentOrder
}