package by.pilipuk.order.business.repository

import by.pilipuk.order.exception.ApplicationException
import by.pilipuk.order.exception.ApplicationExceptionCode
import by.pilipuk.order.model.entity.Order
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.repository.findByIdOrNull

interface OrderRepository : JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {

    fun findByIdOrThrow(id: Long): Order {
        return findByIdOrNull(id)
            ?: throw ApplicationException.create(ApplicationExceptionCode.NOT_FOUND_BY_ID, id)
    }
}