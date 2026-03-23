package by.pilipuk.gateway.business.service

import by.pilipuk.gateway.business.mapper.AuthMapper.toAuthResponse
import by.pilipuk.gateway.core.security.JwtTokenProvider
import by.pilipuk.gateway.dto.AuthRequest
import by.pilipuk.gateway.dto.AuthResponse
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Service

@Service
class AuthService(
    val authenticationManager: AuthenticationManager,
    val userDetailsServiceImpl: UserDetailsServiceImpl,
    val jwtTokenProvider: JwtTokenProvider
) {

    fun login(authRequest: AuthRequest): AuthResponse {
        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(
                authRequest.username,
                authRequest.password
            )
        )

        return userDetailsServiceImpl.loadUserByUsername(authRequest.username).let {
            it.toAuthResponse(
                jwtTokenProvider.generateAccessToken(it),
                jwtTokenProvider.generateRefreshToken(it)
            )
        }
    }
}