package by.pilipuk.kitchen.spec.entrypoint

import by.pilipuk.commonCore.spec.entrypoint.BaseControllerTest
import by.pilipuk.kitchen.dto.OrderDto
import by.pilipuk.kitchen.entrypoint.scheduler.KitchenScheduler
import by.pilipuk.kitchen.environment.service.OrderTestService
import by.pilipuk.kitchen.model.enums.Status
import org.awaitility.Awaitility.await
import org.junit.jupiter.api.Assertions.assertEquals
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.kafka.KafkaContainer
import org.testcontainers.postgresql.PostgreSQLContainer
import java.util.concurrent.TimeUnit
import kotlin.test.Test

@Testcontainers
class OrderListenerTest : BaseControllerTest() {

    companion object {
        @Container
        val postgres: PostgreSQLContainer = PostgreSQLContainer(
            "postgres:14-alpine"
        )

        val kafka: KafkaContainer = KafkaContainer(
            "apache/kafka:4.0.1"
        )
    }

    @Autowired
    private lateinit var orderTestService: OrderTestService

    @Autowired
    private lateinit var kafkaTemplate: KafkaTemplate<String, Any>

    @MockitoBean
    private lateinit var kitchenScheduler: KitchenScheduler

    @Test
    fun `listen should save order with ACCEPTED status`() {
        // given
        val event = orderTestService.createOrderCreatedEvent()
        val jsonEvent = objectMapper.writeValueAsString(event)
        val id = orderTestService.saveOrder()

        // when
        kafkaTemplate.send("orders", jsonEvent)

        // then
        await()
            .atMost(10, TimeUnit.SECONDS)
            .pollInterval(500, TimeUnit.MILLISECONDS)
            .untilAsserted {
                val savedKitchenDto = orderTestService.getOrderDtoFromDB(id)
                val expectedKitchenDto = orderTestService.createOrderDto()
                savedKitchenDto.copy(status = Status.ACCEPTED.toString())

                assertEquals(expectedKitchenDto, savedKitchenDto)
            }
    }
}