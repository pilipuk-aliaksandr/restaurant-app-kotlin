package by.pilipuk.kitchen

import by.pilipuk.commonCore.model.entity.CommonCoreMarker
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.persistence.autoconfigure.EntityScan
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication(scanBasePackageClasses = [CommonCoreMarker::class, KitchenApiApp::class])
@EntityScan(basePackageClasses = [CommonCoreMarker::class, KitchenApiApp::class])
@EnableJpaRepositories(basePackageClasses = [CommonCoreMarker::class, KitchenApiApp::class])
class KitchenApiApp

fun main(args: Array<String>) {
    runApplication<KitchenApiApp>(*args)
}