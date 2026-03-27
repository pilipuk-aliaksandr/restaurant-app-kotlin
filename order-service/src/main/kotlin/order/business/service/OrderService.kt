package by.pilipuk.order.business.service

import by.pilipuk.commonKafka.business.repository.OutboxEventRepository
import by.pilipuk.commonKafka.model.dto.OrderReadyEvent
import by.pilipuk.order.business.mapper.findOrdersByRequestDto
import by.pilipuk.order.business.mapper.toDto
import by.pilipuk.order.business.mapper.toEntity
import by.pilipuk.order.business.mapper.toOutboxEvent
import by.pilipuk.order.business.repository.OrderRepository
import by.pilipuk.order.dto.OrderDto
import by.pilipuk.order.dto.OrderRequestDto
import by.pilipuk.order.dto.OrderWriteDto
import by.pilipuk.order.model.enum.Status
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class OrderService(
    private val orderRepository: OrderRepository,
    private val outboxEventRepository: OutboxEventRepository
) {

    private val log = KotlinLogging.logger {}

    fun create(orderWriteDto: OrderWriteDto): OrderDto {
        val savedOrder = orderWriteDto.toEntity()
            .let { orderRepository.save(it) }

        savedOrder.toOutboxEvent()
            .let { outboxEventRepository.save(it) }
            .also { log.info {"The order: ${savedOrder.id} is created at ${LocalDateTime.now()}}"} }

        return savedOrder.toDto()
    }

    fun findById(id: Long): OrderDto {

        return orderRepository.findByIdOrThrow(id)
            .toDto()
    }

    fun findOrders(orderRequestDto: OrderRequestDto): List<OrderDto> {
        val spec = findOrdersByRequestDto(orderRequestDto)

        return orderRepository.findAll(spec)
            .map { it.toDto() }
    }

    fun acceptCookedOrders(orderReadyEvent: OrderReadyEvent) {

        orderRepository.findByIdOrThrow(orderReadyEvent.orderId)
            .apply { status = Status.READY }
            .let { orderRepository.save(it) }
            .also { log.info { "The order: ${orderReadyEvent.orderId} is cooked at ${LocalDateTime.now()}}" } }
    }
}