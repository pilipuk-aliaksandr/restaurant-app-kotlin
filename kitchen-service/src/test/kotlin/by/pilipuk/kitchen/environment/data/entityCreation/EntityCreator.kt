package by.pilipuk.kitchen.environment.data.entityCreation

import org.springframework.stereotype.Component

@Component
class EntityCreator(
    val orderCreator: OrderCreator,
    val orderItemCreator: OrderItemCreator
)