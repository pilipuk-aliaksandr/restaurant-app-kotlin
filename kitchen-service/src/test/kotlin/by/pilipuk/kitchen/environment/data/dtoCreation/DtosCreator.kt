package by.pilipuk.kitchen.environment.data.dtoCreation

import org.springframework.stereotype.Component

@Component
class DtosCreator(
    val orderDtosCreator: OrderDtosCreator,
    val orderItemDtosCreator: OrderItemDtosCreator
)