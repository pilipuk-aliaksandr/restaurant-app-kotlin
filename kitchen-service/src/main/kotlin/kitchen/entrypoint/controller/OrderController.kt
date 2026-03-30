package by.pilipuk.kitchen.entrypoint.controller

import by.pilipuk.kitchen.api.KitchenApi
import by.pilipuk.kitchen.business.service.OrderService
import by.pilipuk.kitchen.dto.OrderDto
import by.pilipuk.kitchen.dto.OrderRequestDto
import org.springframework.web.bind.annotation.RestController

@RestController
class OrderController(private val orderService: OrderService) : KitchenApi {

    override fun findById(id: Long): OrderDto {
        return orderService.findById(id)
    }

    override fun findOrders(orderRequestDto: OrderRequestDto): List<OrderDto> {
        return orderService.findOrders(orderRequestDto)
    }
}