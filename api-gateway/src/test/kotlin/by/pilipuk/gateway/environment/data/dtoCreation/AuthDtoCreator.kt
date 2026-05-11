package by.pilipuk.gateway.environment.data.dtoCreation

import by.pilipuk.gateway.core.security.JwtTokenProvider
import by.pilipuk.gateway.dto.AuthRequest
import by.pilipuk.gateway.dto.AuthResponse
import by.pilipuk.gateway.model.dto.UserDetailsDto
import org.springframework.stereotype.Component

@Component
class AuthDtoCreator(
    private val jwtTokenProvider: JwtTokenProvider
) {

    fun createAuthRequest(): AuthRequest = AuthRequest(
        username = "JohnDoe1",
        password = "SuperPassword"
    )

    fun createAuthResponse(userDetailsDto: UserDetailsDto): AuthResponse = AuthResponse(
        id = userDetailsDto.id,
        username = userDetailsDto.username,
        accessToken = jwtTokenProvider.generateAccessToken(userDetailsDto),
        refreshToken = jwtTokenProvider.generateRefreshToken(userDetailsDto)
    )
}