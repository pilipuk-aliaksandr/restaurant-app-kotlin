package by.pilipuk.order.business.repository

import by.pilipuk.order.exception.ApplicationException
import by.pilipuk.order.exception.ApplicationExceptionCode
import by.pilipuk.order.model.entity.OrderItem
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.findByIdOrNull

interface OrderItemRepository : JpaRepository<OrderItem, Long> {

    fun findByIdOrThrow(id: Long): OrderItem {
        return findByIdOrNull(id)
            ?: throw ApplicationException.create(ApplicationExceptionCode.NOT_FOUND_BY_ID, id)
    }
}