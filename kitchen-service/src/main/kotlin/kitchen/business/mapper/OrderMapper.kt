package by.pilipuk.kitchen.business.mapper

import by.pilipuk.kitchen.dto.OrderDto
import by.pilipuk.kitchen.dto.OrderItemsDto
import by.pilipuk.kitchen.model.entity.Order

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
}