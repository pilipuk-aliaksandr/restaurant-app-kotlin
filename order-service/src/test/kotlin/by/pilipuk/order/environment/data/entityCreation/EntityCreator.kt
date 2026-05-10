package by.pilipuk.order.environment.data.entityCreation

import org.springframework.stereotype.Component

@Component
class EntityCreator(
    val orderCreator: OrderCreator,
    val orderItemsCreator: OrderItemsCreator
)