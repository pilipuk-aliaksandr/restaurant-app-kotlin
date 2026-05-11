package by.pilipuk.kitchen.spec.entrypoint

import by.pilipuk.commonCore.spec.entrypoint.BaseControllerTest
import by.pilipuk.kitchen.entrypoint.scheduler.KitchenScheduler
import by.pilipuk.kitchen.environment.service.OrderTestService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.postgresql.PostgreSQLContainer
import kotlin.test.Test

@ActiveProfiles("test")
@Testcontainers
class OrderControllerTest : BaseControllerTest() {

    companion object {
        @Container
        @ServiceConnection
        @JvmStatic
        val postgres: PostgreSQLContainer = PostgreSQLContainer("postgres:14-alpine")
            .withInitScript("init.sql")
    }

    @Autowired
    private lateinit var orderTestService: OrderTestService

    @MockitoBean
    private lateinit var kitchenScheduler: KitchenScheduler

    @Test
    fun `findById should return order by id`() {
        // given
        val expectedOrderDto = orderTestService.createOrderDto()

        // when
        orderTestService.saveOrder()

        // then
        performGetRequest("/orders/{id}", 1, expectedOrderDto)
    }

    @Test
    fun `findOrders should return list of orders`() {
        // given
        val requestDto = orderTestService.createOrderRequestDto()
        val expectedOrderDtos = listOf(orderTestService.createOrderDto())

        // when
        orderTestService.saveOrder()

        // then
        performPostSearchRequest("/orders", requestDto, expectedOrderDtos)
    }
}