package by.pilipuk.order.business.service

import by.pilipuk.order.business.mapper.OrderMapper.toDto
import by.pilipuk.order.business.mapper.OrderMapper.toEntity
import by.pilipuk.order.business.mapper.OrderSpecificationMapper
import by.pilipuk.order.business.repository.OrderRepository
import by.pilipuk.order.dto.OrderDto
import by.pilipuk.order.dto.OrderRequestDto
import by.pilipuk.order.dto.OrderWriteDto
import org.springframework.stereotype.Service

@Service
class OrderService(val orderRepository: OrderRepository) {

    fun create(orderWriteDto: OrderWriteDto): OrderDto {

        return orderWriteDto.toEntity()
            .let { entity -> orderRepository.save(entity) }
            .toDto()
    }

    fun findById(id: Long): OrderDto {

        return orderRepository.findByIdOrThrow(id)
            .toDto()
    }

    fun findOrders(orderRequestDto: OrderRequestDto): List<OrderDto> {
        val spec = OrderSpecificationMapper.findOrdersByRequestDto(orderRequestDto)

        return orderRepository.findAll(spec)
            .map { it.toDto() }
    }
}