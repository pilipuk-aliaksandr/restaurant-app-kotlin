package by.pilipuk.order.spec.entrypoint

import by.pilipuk.commonCore.spec.entrypoint.BaseControllerTest
import by.pilipuk.order.business.mapper.toDto
import by.pilipuk.order.environment.service.OrderTestService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.kafka.autoconfigure.KafkaAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.test.context.ActiveProfiles
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