package by.pilipuk.kitchen.business.service

import by.pilipuk.kitchen.business.mapper.OrderMapper.toDto
import by.pilipuk.kitchen.business.mapper.OrderSpecificationMapper
import by.pilipuk.kitchen.business.repository.OrderRepository
import by.pilipuk.kitchen.dto.OrderDto
import by.pilipuk.kitchen.dto.OrderRequestDto
import org.springframework.stereotype.Service

@Service
class OrderService(val orderRepository: OrderRepository) {

    fun findById(id: Long): OrderDto {
        return orderRepository.findByIdOrThrow(id).toDto()
    }

    fun findOrders(orderRequestDto: OrderRequestDto): List<OrderDto> {
        val spec = OrderSpecificationMapper.findOrdersByRequestDto(orderRequestDto)

        return orderRepository.findAll(spec)
            .map { it.toDto() }
    }
}