package by.pilipuk.order.business.service

import by.pilipuk.commonKafka.business.repository.OutboxEventRepository
import by.pilipuk.commonKafka.model.dto.OrderReadyEvent
import by.pilipuk.order.business.mapper.OrderMapper.toDto
import by.pilipuk.order.business.mapper.OrderMapper.toEntity
import by.pilipuk.order.business.mapper.OrderMapper.toOutboxEvent
import by.pilipuk.order.business.mapper.OrderSpecificationMapper
import by.pilipuk.order.business.repository.OrderRepository
import by.pilipuk.order.dto.OrderDto
import by.pilipuk.order.dto.OrderRequestDto
import by.pilipuk.order.dto.OrderWriteDto
import by.pilipuk.order.model.enum.Status
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class OrderService(val orderRepository: OrderRepository, val outboxEventRepository: OutboxEventRepository) {

    val log = KotlinLogging.logger {}

    fun create(orderWriteDto: OrderWriteDto): OrderDto {
        val savedOrder = orderWriteDto.toEntity()
            .let { entity -> orderRepository.save(entity) }
        savedOrder.toOutboxEvent()
            .let { event -> outboxEventRepository.save(event) }
        log.info {"The order: ${savedOrder.id} is created at ${LocalDateTime.now()}}"}

        return savedOrder.toDto()
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

    fun acceptCookedOrders(orderReadyEvent: OrderReadyEvent) {
        val order = orderRepository.findByIdOrThrow(orderReadyEvent.orderId)

        order.status = Status.READY
        orderRepository.save(order)
        log.info {"The order: ${orderReadyEvent.orderId} is cooked at ${LocalDateTime.now()}}"}
    }
}