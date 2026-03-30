package by.pilipuk.order.business.mapper

import by.pilipuk.order.dto.OrderRequestDto
import by.pilipuk.order.model.entity.Order
import jakarta.persistence.criteria.Path
import org.springframework.data.jpa.domain.Specification

private fun <T> checkForSpec(values: List<T>?, field: String, isLike: Boolean = false): Specification<Order> =
    Specification { root, _, cb ->
        val path: Path<T> = root.get(field)
        when {
            values.isNullOrEmpty() -> cb.conjunction()
            values.size == 1 -> if (isLike) cb.like(path as Path<String>, "%${values.first()}%")
            else cb.equal(path, values.first())

            else -> path.`in`(values)
        }
    }

fun findOrdersByRequestDto(request: OrderRequestDto): Specification<Order> {
    return Specification.allOf(
        hasIds(request.id),
        hasTableNumbers(request.tableNumber),
        hasStatuses(request.status),
        hasItems(request.items)
    )
}

private fun hasIds(ids: List<Long>?) = checkForSpec(ids, "id")

private fun hasTableNumbers(numbers: List<Int>?) = checkForSpec(numbers, "tableNumber")

private fun hasStatuses(statuses: List<String>?) = checkForSpec(statuses, "status", isLike = true)

private fun hasItems(items: List<String>?) = Specification<Order> { root, _, cb ->
    when {
        items.isNullOrEmpty() -> cb.conjunction()
        items.size == 1 -> cb.like(root.join<Any, Any>("items").get("name"), "%${items.first()}%")
        else -> root.join<Any, Any>("items").get<String>("name").`in`(items)
    }
}