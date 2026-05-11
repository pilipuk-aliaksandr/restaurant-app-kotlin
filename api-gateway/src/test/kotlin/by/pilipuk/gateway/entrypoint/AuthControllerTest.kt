package by.pilipuk.gateway.entrypoint

import by.pilipuk.commonCore.spec.entrypoint.BaseControllerTest
import by.pilipuk.gateway.core.security.JwtTokenProvider
import by.pilipuk.gateway.environment.service.AuthTestService
import org.mockito.Mockito.doReturn
import org.mockito.kotlin.any
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers
import org.testcontainers.postgresql.PostgreSQLContainer
import kotlin.test.Test

@ActiveProfiles("test")
@Testcontainers
class AuthControllerTest : BaseControllerTest() {

    companion object {
        @Container
        @ServiceConnection
        val postgres: PostgreSQLContainer = PostgreSQLContainer("postgres:14-alpine")
            .withInitScript("init.sql")
    }

    @Autowired
    private lateinit var authTestService: AuthTestService

    @MockitoSpyBean
    private lateinit var jwtTokenProvider: JwtTokenProvider

    @Test
    fun `authenticateCurrentUser should return auth response with tokens`() {
        // given
        val authRequest = authTestService.createAuthRequest()

        val fixedAccessToken = "fixed_access_token"
        val fixedRefreshToken = "fixed_refresh_token"

        // when
        doReturn(fixedAccessToken).`when`(jwtTokenProvider).generateAccessToken(any())
        doReturn(fixedRefreshToken).`when`(jwtTokenProvider).generateRefreshToken(any())

        authTestService.saveUser()
        val expectedAuthResponse = authTestService.createAuthResponse()

        // then
        performAuthRequest("/v1/login", authRequest, expectedAuthResponse)
    }

    @Test
    fun `registerNewUser should create user and return 201`() {
        // given — when — then
        performPostRequest("/v1/registration", authTestService.createUserWriteDto())
    }
}