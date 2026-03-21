package by.pilipuk.gateway.entrypoint.controller

import by.pilipuk.gateway.api.AuthApi
import by.pilipuk.gateway.business.service.AuthService
import by.pilipuk.gateway.business.service.UserService
import by.pilipuk.gateway.dto.AuthRequest
import by.pilipuk.gateway.dto.AuthResponse
import by.pilipuk.gateway.dto.UserWriteDto
import org.springframework.web.bind.annotation.RestController

@RestController
class AuthController(val userService: UserService, val authService: AuthService) : AuthApi {

    override fun login(authRequest: AuthRequest): AuthResponse {
        return authService.login(authRequest)
    }

    override fun registerNewUser(userWriteDto: UserWriteDto) {
        userService.saveUser(userWriteDto)
    }
}