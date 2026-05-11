package by.pilipuk.order.environment.data.dtoCreation

import org.springframework.stereotype.Component

@Component
class DtosCreator(
    val orderDtosCreator: OrderDtosCreator,
    val orderItemsDtosCreator: OrderItemsDtosCreator
)