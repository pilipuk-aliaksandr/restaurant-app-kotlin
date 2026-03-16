package by.pilipuk.order.entrypoint.controller

import by.pilipuk.order.api.OrdersApi
import by.pilipuk.order.dto.OrderDto
import by.pilipuk.order.dto.OrderRequestDto
import by.pilipuk.order.dto.OrderWriteDto
import by.pilipuk.order.business.service.OrderService
import org.springframework.web.bind.annotation.RestController

@RestController
class OrderController(val orderService: OrderService) : OrdersApi {

    override fun create(orderWriteDto: OrderWriteDto): OrderDto {
        return orderService.create(orderWriteDto)
    }

    override fun findById(id: Long): OrderDto {
        return orderService.findById(id)
    }

    override fun findOrders(orderRequestDto: OrderRequestDto): List<OrderDto> {
        return orderService.findOrders(orderRequestDto)
    }
}