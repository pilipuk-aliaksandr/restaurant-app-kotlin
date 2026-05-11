package by.pilipuk.order.spec.entrypoint

import by.pilipuk.commonCore.spec.entrypoint.BaseControllerTest
import by.pilipuk.order.environment.service.OrderTestService
import org.awaitility.Awaitility.await
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.test.context.ActiveProfiles
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.kafka.KafkaContainer
import org.testcontainers.postgresql.PostgreSQLContainer
import java.util.concurrent.TimeUnit
import kotlin.test.Test

@ActiveProfiles("test")
@Testcontainers
class OrderListenerTest : BaseControllerTest() {

    companion object {
        @Container
        @ServiceConnection
        @JvmStatic
        val postgres: PostgreSQLContainer = PostgreSQLContainer("postgres:14-alpine")
            .withInitScript("init.sql")

        @Container
        @ServiceConnection
        @JvmStatic
        val kafka: KafkaContainer = KafkaContainer(
            "apache/kafka:4.0.1"
        )
    }

    @Autowired
    private lateinit var orderTestService: OrderTestService

    @Autowired
    private lateinit var kafkaTemplate: KafkaTemplate<String, Any>

    @Test
    fun `listen should update order status to READY`() {
        // given
        val event = orderTestService.createOrderReadyEvent()
        val jsonEvent = objectMapper.writeValueAsString(event)
        val id = orderTestService.saveOrderWithStatusReady()

        // when
        kafkaTemplate.send("ready_orders", jsonEvent)

        // then
        await()
            .atMost(10, TimeUnit.SECONDS)
            .pollInterval(500, TimeUnit.MILLISECONDS)
            .untilAsserted {
                val savedOrderDto = orderTestService.getOrderDtoFromDB(id)
                val expectedOrderDto = orderTestService.createOrderDto().copy(status = "READY")

                kotlin.test.assertEquals(expectedOrderDto, savedOrderDto)
            }
    }
}