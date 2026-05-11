package by.pilipuk.order.environment.service

import by.pilipuk.commonKafka.model.dto.OrderReadyEvent
import by.pilipuk.order.business.mapper.toDto
import by.pilipuk.order.business.repository.OrderRepository
import by.pilipuk.order.dto.OrderDto
import by.pilipuk.order.dto.OrderRequestDto
import by.pilipuk.order.dto.OrderWriteDto
import by.pilipuk.order.model.entity.Order
import by.pilipuk.order.environment.data.dtoCreation.DtosCreator
import by.pilipuk.order.environment.data.entityCreation.EntityCreator
import by.pilipuk.order.environment.data.eventCreation.EventCreator
import org.hibernate.Hibernate
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
open class OrderTestService(
    private val entityCreator: EntityCreator,
    private val dtosCreator: DtosCreator,
    private val eventCreator: EventCreator,
    private val orderRepository: OrderRepository,
) {

    fun createOrder(): Order {
        val order = entityCreator.orderCreator.createOrder()
        val orderItems = entityCreator.orderItemsCreator.createOrderItems(order)
        order.items = orderItems.toMutableList()
        return order
    }

    fun saveOrder(): Long {
        val order = entityCreator.orderCreator.saveTestOrderToDB(
            entityCreator.orderCreator.createOrder()
        )
        val orderItems = entityCreator.orderItemsCreator.saveTestOrderItemsToDB(order)
        order.items = orderItems.toMutableList()
        return order.id!!
    }

    fun saveOrderWithStatusReady(): Long {
        val order = entityCreator.orderCreator.saveTestOrderToDB(
            entityCreator.orderCreator.createOrderWithStatusReady()
        )
        val orderItems = entityCreator.orderItemsCreator.saveTestOrderItemsToDB(order)
        order.items = orderItems.toMutableList()
        return order.id!!
    }

    fun createOrderWriteDto(): OrderWriteDto {
        val orderItemsWriteDto = dtosCreator.orderItemsDtosCreator.createOrderItemsWriteDto()
        return dtosCreator.orderDtosCreator.createOrderWriteDto(orderItemsWriteDto)
    }

    fun createOrderDto(): OrderDto {
        val orderItemsDto = dtosCreator.orderItemsDtosCreator.createOrderItemsDto()
        return dtosCreator.orderDtosCreator.createOrderDto(orderItemsDto)
    }

    fun createOrderRequestDto(): OrderRequestDto =
        dtosCreator.orderDtosCreator.createOrderRequestDto()

    fun createOrderReadyEvent(): OrderReadyEvent =
        eventCreator.createOrderReadyEvent()

    @Transactional(readOnly = true)
    open fun getOrderDtoFromDB(id: Long): OrderDto {
        val savedOrder = orderRepository.findByIdOrThrow(id)
        Hibernate.initialize(savedOrder.items)
        return savedOrder.toDto()
    }
}