package by.pilipuk.gateway

import by.pilipuk.commonCore.CommonCoreMarker
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.persistence.autoconfigure.EntityScan
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication(scanBasePackageClasses = [CommonCoreMarker::class, ApiGatewayApp::class])
@EntityScan(basePackageClasses = [CommonCoreMarker::class, ApiGatewayApp::class])
@EnableJpaRepositories(basePackageClasses = [CommonCoreMarker::class, ApiGatewayApp::class])
@EnableJpaAuditing
@ConfigurationPropertiesScan
class ApiGatewayApp

fun main(args: Array<String>) {
    runApplication<ApiGatewayApp>(*args)
}
