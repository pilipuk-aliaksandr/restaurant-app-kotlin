package by.pilipuk.order.environment.data.dtoCreation

import by.pilipuk.order.dto.OrderDto
import by.pilipuk.order.dto.OrderItemsDto
import by.pilipuk.order.dto.OrderItemsWriteDto
import by.pilipuk.order.dto.OrderRequestDto
import by.pilipuk.order.dto.OrderWriteDto
import by.pilipuk.order.model.enum.Status
import org.springframework.stereotype.Component

@Component
class OrderDtosCreator {

    fun createOrderWriteDto(items: List<OrderItemsWriteDto>): OrderWriteDto =
        OrderWriteDto(1, items)

    fun createOrderDto(items: List<OrderItemsDto>): OrderDto =
        OrderDto(
            id = 1L,
            tableNumber = 1,
            status = Status.CREATED.toString(),
            items = items
        )

    fun createOrderRequestDto(): OrderRequestDto = OrderRequestDto()
}