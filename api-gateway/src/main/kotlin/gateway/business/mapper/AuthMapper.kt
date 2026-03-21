package by.pilipuk.gateway.business.mapper

import by.pilipuk.gateway.dto.AuthResponse
import by.pilipuk.gateway.model.dto.UserDetailsDto

object AuthMapper {

    fun UserDetailsDto.toAuthResponse(accessToken: String, refreshToken: String) = AuthResponse(
        id = this@toAuthResponse.id,
        username = this@toAuthResponse.username,
        accessToken = accessToken,
        refreshToken = refreshToken
    )

}