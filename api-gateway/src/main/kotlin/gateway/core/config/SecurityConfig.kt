package by.pilipuk.gateway.core.config

import by.pilipuk.gateway.core.security.JwtTokenFilter
import by.pilipuk.gateway.core.security.JwtTokenProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig(private val jwtTokenProvider: JwtTokenProvider) {

    @Bean
    fun passwordEncoder(): PasswordEncoder = BCryptPasswordEncoder()

    @Bean
    fun authenticationManager(config: AuthenticationConfiguration): AuthenticationManager =
        config.authenticationManager

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http {
            csrf { disable() }
            cors { }
            httpBasic { disable() }
            formLogin { disable() }
            anonymous { disable() }

            sessionManagement {
                sessionCreationPolicy = SessionCreationPolicy.STATELESS
            }

            exceptionHandling {
                authenticationEntryPoint = { _, response, _ ->
                    response.status = HttpStatus.UNAUTHORIZED.value()
                    response.writer.write("Unauthorized")
                }
                accessDeniedHandler = { _, response, _ ->
                    response.status = HttpStatus.FORBIDDEN.value()
                    response.writer.write("Forbidden")
                }
            }

            authorizeHttpRequests {
                authorize("/v1/login", permitAll)
                authorize("/v1/registration", permitAll)
                authorize(anyRequest, authenticated)
            }

            addFilterBefore<UsernamePasswordAuthenticationFilter>(JwtTokenFilter(jwtTokenProvider))
        }

        return http.build()
    }
}