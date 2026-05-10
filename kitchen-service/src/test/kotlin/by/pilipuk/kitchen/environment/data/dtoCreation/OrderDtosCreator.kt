package by.pilipuk.kitchen.environment.data.dtoCreation

import by.pilipuk.kitchen.dto.OrderDto
import by.pilipuk.kitchen.dto.OrderRequestDto
import by.pilipuk.kitchen.model.enums.Status
import org.springframework.stereotype.Component

@Component
class OrderDtosCreator {

    fun createOrderDto(): OrderDto = OrderDto(
        id = 1L,
        orderId = 1L,
        status = Status.ACCEPTED.toString()
    )

    fun createOrderRequestDto(): OrderRequestDto = OrderRequestDto()
}