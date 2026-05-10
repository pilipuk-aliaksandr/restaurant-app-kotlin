package by.pilipuk.kitchen.environment.data.dtoCreation

import by.pilipuk.kitchen.dto.OrderDto
import by.pilipuk.kitchen.dto.OrderItemsDto
import org.springframework.stereotype.Component

@Component
class OrderItemDtosCreator {

    private fun createOrderItemDto(id: Long, orderDto: OrderDto, name: String): OrderItemsDto =
        OrderItemsDto(
            id = id,
            orderId = orderDto.id,
            itemName = name,
            cooked = false
        )

    fun createOrderItemsDto(orderDto: OrderDto): List<OrderItemsDto> = listOf(
        createOrderItemDto(1L, orderDto, "Pizza"),
        createOrderItemDto(2L, orderDto, "Coca-Cola")
    )
}