package by.pilipuk.order.spec.entrypoint

import by.pilipuk.commonCore.spec.entrypoint.BaseControllerTest
import by.pilipuk.order.business.mapper.toDto
import by.pilipuk.order.environment.service.OrderTestService
import org.springframework.beans.factory.annotation.Autowired
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.postgresql.PostgreSQLContainer
import kotlin.test.Test

@Testcontainers
class OrderControllerTest : BaseControllerTest() {

    companion object {
        @Container
        val postgres: PostgreSQLContainer = PostgreSQLContainer(
            "postgres:14-alpine"
        )
    }

    @Autowired
    private lateinit var orderTestService: OrderTestService

    @Test
    fun `create order should return created order`() {
        // given
        val expectedOrderDto = orderTestService.createOrder().toDto()

        // then
        performPostRequest("/new", orderTestService.createOrderWriteDto(), expectedOrderDto)
    }

    @Test
    fun `findById should return order by id`() {
        // given
        val expectedOrderDto = orderTestService.createOrderDto()

        // when
        orderTestService.saveOrder()

        // then
        performGetRequest("/{id}", 1, expectedOrderDto)
    }

    @Test
    fun `findOrders should return list of orders`() {
        // given
        val orderRequestDto = orderTestService.createOrderRequestDto()
        val expectedOrderDto = listOf(orderTestService.createOrderDto())

        // when
        orderTestService.saveOrder()

        // then
        performPostSearchRequest("/", orderRequestDto, expectedOrderDto)
    }
}