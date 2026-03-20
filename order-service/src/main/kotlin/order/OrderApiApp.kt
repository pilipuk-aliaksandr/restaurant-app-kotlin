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

@SpringBootApplication(scanBasePackageClasses = [CommonKafkaMarker::class, CommonCoreMarker::class, OrderApiApp::class])
@EntityScan(basePackageClasses = [CommonKafkaMarker::class, CommonCoreMarker::class, OrderApiApp::class])
@EnableJpaRepositories(basePackageClasses = [CommonKafkaMarker::class, CommonCoreMarker::class, OrderApiApp::class])
@EnableKafka
@EnableScheduling
@EnableJpaAuditing
class OrderApiApp

fun main(args: Array<String>) {
    runApplication<OrderApiApp>(*args)
}