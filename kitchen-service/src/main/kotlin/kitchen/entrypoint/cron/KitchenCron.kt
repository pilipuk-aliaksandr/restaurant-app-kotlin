package by.pilipuk.kitchen.entrypoint.cron

import by.pilipuk.kitchen.business.service.OrderService
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class KitchenCron(val orderService: OrderService) {

    @Scheduled(fixedDelay = 10000)
    fun cookOneItem() {
        orderService.processed()
    }
}