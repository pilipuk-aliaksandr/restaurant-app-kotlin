package by.pilipuk.kitchen.entrypoint.scheduler

import by.pilipuk.kitchen.business.service.OrderService
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class KitchenScheduler(private val orderService: OrderService) {

    @Scheduled(fixedDelay = 10000)
    fun cookOneItemSchedule() {
        orderService.processed()
    }
}