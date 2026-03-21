package by.pilipuk.gateway.core.util

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "spring.security.jwt")
data class JwtProperties(
    var secret: String,
    var access: Long,
    var refresh: Long
)