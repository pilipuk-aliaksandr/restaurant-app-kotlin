package by.pilipuk.order.environment.data.dtoCreation

import by.pilipuk.order.dto.OrderItemsDto
import by.pilipuk.order.dto.OrderItemsWriteDto
import org.springframework.stereotype.Component

@Component
class OrderItemsDtosCreator {

    private fun createOrderItemWriteDto(name: String): OrderItemsWriteDto =
        OrderItemsWriteDto(name)

    fun createOrderItemsWriteDto(): List<OrderItemsWriteDto> = listOf(
        createOrderItemWriteDto("Pizza"),
        createOrderItemWriteDto("Coca-Cola")
    )

    private fun createOrderItemDto(id: Long, name: String): OrderItemsDto =
        OrderItemsDto(
            id = id,
            itemName = name
        )

    fun createOrderItemsDto(): List<OrderItemsDto> = listOf(
        createOrderItemDto(1L, "Pizza"),
        createOrderItemDto(2L, "Coca-Cola")
    )
}