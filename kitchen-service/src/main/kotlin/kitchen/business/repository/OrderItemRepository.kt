package by.pilipuk.kitchen.business.repository

import by.pilipuk.kitchen.core.exception.ApplicationException
import by.pilipuk.kitchen.core.exception.ApplicationExceptionCode
import by.pilipuk.kitchen.model.entity.OrderItem
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.findByIdOrNull
import java.util.Optional

interface OrderItemRepository : JpaRepository<OrderItem, Long> {

    fun findByIdOrThrow(id: Long): OrderItem {
        return findByIdOrNull(id)
            ?: throw ApplicationException.create(ApplicationExceptionCode.NOT_FOUND_BY_ID, id)
    }

    fun findFirstByCookedFalseOrderByCreatedAtAsc(): OrderItem?
}