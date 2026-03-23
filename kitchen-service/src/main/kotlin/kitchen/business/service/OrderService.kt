package by.pilipuk.kitchen.business.service

import by.pilipuk.commonKafka.business.repository.OutboxEventRepository
import by.pilipuk.commonKafka.model.dto.OrderCreatedEvent
import by.pilipuk.kitchen.business.mapper.OrderMapper.toDto
import by.pilipuk.kitchen.business.mapper.OrderMapper.toEntity
import by.pilipuk.kitchen.business.mapper.OrderMapper.toOutboxEvent
import by.pilipuk.kitchen.business.mapper.OrderSpecificationMapper
import by.pilipuk.kitchen.business.repository.OrderItemRepository
import by.pilipuk.kitchen.business.repository.OrderRepository
import by.pilipuk.kitchen.dto.OrderDto
import by.pilipuk.kitchen.dto.OrderRequestDto
import by.pilipuk.kitchen.model.enums.Status
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class OrderService(
    val orderRepository: OrderRepository,
    val orderItemRepository: OrderItemRepository,
    val outboxEventRepository: OutboxEventRepository
) {

    val log = KotlinLogging.logger {}

    fun findById(id: Long): OrderDto {

        return orderRepository.findByIdOrThrow(id)
            .toDto()
    }

    fun findOrders(orderRequestDto: OrderRequestDto): List<OrderDto> {
        val spec = OrderSpecificationMapper.findOrdersByRequestDto(orderRequestDto)

        return orderRepository.findAll(spec)
            .map { it.toDto() }
    }

    @Transactional
    fun processed() {
        val item = orderItemRepository.findFirstByCookedFalseOrderByCreatedAtAsc()
            ?: return

        item.cooked = true
        log.info {"The item: ${item.name} is cooked at ${LocalDateTime.now()}}"}

        val order = item.order

        if (order.status == Status.ACCEPTED) {
            order.status = Status.COOKING
        }

        if (order.items.all { it.cooked }) {
            order.status = Status.COMPLETED
            log.info {"The order: ${order.orderId} is cooked at ${LocalDateTime.now()}}"}

            outboxEventRepository.save(order.toOutboxEvent())
        }
    }

    @Transactional
    fun acceptNewOrder(orderCreatedEvent: OrderCreatedEvent) {
        val order = orderCreatedEvent.toEntity()
        order.status = Status.ACCEPTED
        orderRepository.save(order).also {
            log.info { "The new order: ${orderCreatedEvent.orderId} is accepted at ${LocalDateTime.now()}}" }
        }
    }
}