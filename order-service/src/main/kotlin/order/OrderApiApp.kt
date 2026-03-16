package by.pilipuk.order

import by.pilipuk.commonCore.model.entity.CommonCoreMarker
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.persistence.autoconfigure.EntityScan
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication(scanBasePackageClasses = [CommonCoreMarker::class, OrderApiApp::class])
@EntityScan(basePackageClasses = [CommonCoreMarker::class, OrderApiApp::class])
@EnableJpaRepositories(basePackageClasses = [CommonCoreMarker::class, OrderApiApp::class])
class OrderApiApp

fun main(args: Array<String>) {
    runApplication<OrderApiApp>(*args)
}