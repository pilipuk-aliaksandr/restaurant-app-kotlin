package by.pilipuk.order.business.mapper

import by.pilipuk.order.dto.OrderItemsDto
import by.pilipuk.order.dto.OrderItemsWriteDto
import by.pilipuk.order.model.entity.Order
import by.pilipuk.order.model.entity.OrderItem

fun OrderItemsWriteDto.toEntity(currentOrder: Order) = OrderItem().apply {
    name = this@toEntity.itemName
    order = currentOrder
}

fun OrderItem.toDto() = OrderItemsDto(
    id = this.id,
    itemName = this.name
)