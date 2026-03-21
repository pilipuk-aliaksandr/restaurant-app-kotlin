package by.pilipuk.gateway.core.security

import by.pilipuk.gateway.business.service.UserDetailsServiceImpl
import by.pilipuk.gateway.core.util.JwtProperties
import by.pilipuk.gateway.dto.AuthResponse
import by.pilipuk.gateway.model.dto.UserDetailsDto
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import jakarta.annotation.PostConstruct
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.Date
import javax.crypto.SecretKey

@Service
class JwtTokenProvider(
    private val jwtProperties: JwtProperties,
    private val userDetailsServiceImpl: UserDetailsServiceImpl
) {
    private lateinit var key: SecretKey

    @PostConstruct
    fun init() {
        val keyBytes = Decoders.BASE64.decode(jwtProperties.secret)
        key = Keys.hmacShaKeyFor(keyBytes)
    }

    fun generateAccessToken(user: UserDetailsDto): String {
        val validity = Instant.now()
            .plus(jwtProperties.access, ChronoUnit.HOURS)

        return Jwts.builder()
            .subject(user.username)
            .claim("id", user.id)
            .claim("roles", user.authorities)
            .expiration(Date.from(validity))
            .signWith(key)
            .compact()
    }

    fun generateRefreshToken(user: UserDetailsDto): String {
        val validity = Instant.now()
            .plus(jwtProperties.refresh, ChronoUnit.DAYS)

        return Jwts.builder()
            .subject(user.username)
            .claim("id", user.id)
            .expiration(Date.from(validity))
            .signWith(key)
            .compact()
    }

    fun refreshUserTokens(refreshToken: String): AuthResponse {
        if (!isValid(refreshToken)) {
            throw AccessDeniedException("Token is not valid")
        }

        val userDetailsDto = userDetailsServiceImpl.loadUserByUsername(getUsername(refreshToken))

        return AuthResponse(
            id = userDetailsDto.id,
            username = userDetailsDto.username,
            accessToken = generateAccessToken(userDetailsDto),
            refreshToken = generateRefreshToken(userDetailsDto)
        )
    }

    fun isValid(token: String): Boolean =
        parseClaims(token).expiration.after(Date())

    private fun getUsername(token: String): String =
        parseClaims(token).subject

    fun getAuthentication(token: String): Authentication {
        val username = getUsername(token)
        val userDetails = userDetailsServiceImpl.loadUserByUsername(username)

        return UsernamePasswordAuthenticationToken(
            userDetails,
            null,
            userDetails.authorities
        )
    }

    private fun parseClaims(token: String) =
        Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token)
            .payload
}