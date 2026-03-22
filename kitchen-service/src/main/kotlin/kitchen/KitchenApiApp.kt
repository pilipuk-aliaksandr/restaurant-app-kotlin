package by.pilipuk.kitchen

import by.pilipuk.commonCore.CommonCoreMarker
import by.pilipuk.commonKafka.CommonKafkaMarker
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.persistence.autoconfigure.EntityScan
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication(scanBasePackageClasses = [CommonKafkaMarker::class, CommonCoreMarker::class, KitchenApiApp::class])
@EntityScan(basePackageClasses = [CommonKafkaMarker::class, CommonCoreMarker::class, KitchenApiApp::class])
@EnableJpaRepositories(basePackageClasses = [CommonKafkaMarker::class, CommonCoreMarker::class, KitchenApiApp::class])
@EnableKafka
@EnableScheduling
@EnableJpaAuditing
class KitchenApiApp

fun main(args: Array<String>) {
    runApplication<KitchenApiApp>(*args)
}