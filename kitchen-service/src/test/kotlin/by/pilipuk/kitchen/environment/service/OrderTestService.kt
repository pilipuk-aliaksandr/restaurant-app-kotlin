package by.pilipuk.kitchen.environment.service

import by.pilipuk.commonKafka.model.dto.OrderCreatedEvent
import by.pilipuk.kitchen.business.mapper.toDto
import by.pilipuk.kitchen.business.repository.OrderRepository
import by.pilipuk.kitchen.dto.OrderDto
import by.pilipuk.kitchen.dto.OrderRequestDto
import by.pilipuk.kitchen.environment.data.dtoCreation.DtosCreator
import by.pilipuk.kitchen.environment.data.entityCreation.EntityCreator
import by.pilipuk.kitchen.environment.data.eventCreation.EventCreator
import by.pilipuk.kitchen.model.entity.Order
import org.hibernate.Hibernate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class OrderTestService(
    private val entityCreator: EntityCreator,
    private val dtosCreator: DtosCreator,
    private val eventCreator: EventCreator,
    private val orderRepository: OrderRepository,
) {

    fun createOrder(): Order {
        val order = entityCreator.orderCreator.createOrder()
        val orderItems = entityCreator.orderItemCreator.createOrderItems(order)
        order.items = orderItems.toMutableList()
        return order
    }

    fun saveOrder(): Long {
        val order = entityCreator.orderCreator.saveOrder(
            entityCreator.orderCreator.createOrder()
        )
        val orderItems = entityCreator.orderItemCreator.saveOrderItems(order)
        order.items = orderItems.toMutableList()
        return order.id!!
    }

    fun createOrderDto(): OrderDto {
        val orderDto = dtosCreator.orderDtosCreator.createOrderDto()
        val orderItemsDto = dtosCreator.orderItemDtosCreator.createOrderItemsDto(orderDto)
        return orderDto.copy(items = orderItemsDto.toMutableList())
    }

    fun createOrderRequestDto(): OrderRequestDto =
        dtosCreator.orderDtosCreator.createOrderRequestDto()

    fun createOrderCreatedEvent(): OrderCreatedEvent =
        eventCreator.createOrderCreatedEvent()

    @Transactional
    fun getOrderDtoFromDB(id: Long): OrderDto {
        val savedOrder = orderRepository.findByIdOrThrow(id)
        Hibernate.initialize(savedOrder.items)
        return savedOrder.toDto()
    }
}