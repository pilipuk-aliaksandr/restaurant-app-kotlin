package by.pilipuk.order.business.mapper

import by.pilipuk.order.dto.OrderDto
import by.pilipuk.order.dto.OrderItemsDto
import by.pilipuk.order.dto.OrderWriteDto
import by.pilipuk.order.model.entity.Order
import by.pilipuk.order.model.entity.OrderItem
import by.pilipuk.order.model.enum.Status

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
        items = this.items.map { orderItem ->  OrderItemsDto(
            id = orderItem.id,
            itemName = orderItem.name,
        )}
    )
}