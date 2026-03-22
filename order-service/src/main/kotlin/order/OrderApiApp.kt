package by.pilipuk.order

import by.pilipuk.commonCore.CommonCoreMarker
import by.pilipuk.commonKafka.CommonKafkaMarker
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.persistence.autoconfigure.EntityScan
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication(scanBasePackageClasses = [CommonCoreMarker::class, CommonKafkaMarker::class, OrderApiApp::class])
@EntityScan(basePackageClasses = [CommonCoreMarker::class, CommonKafkaMarker::class, OrderApiApp::class])
@EnableJpaRepositories(basePackageClasses = [CommonCoreMarker::class, CommonKafkaMarker::class, OrderApiApp::class])
@EnableJpaAuditing
@EnableKafka
class OrderApiApp

fun main(args: Array<String>) {
    runApplication<OrderApiApp>(*args)
}