package by.pilipuk.gateway.environment.service

import by.pilipuk.gateway.dto.AuthRequest
import by.pilipuk.gateway.dto.AuthResponse
import by.pilipuk.gateway.dto.UserWriteDto
import by.pilipuk.gateway.environment.data.dtoCreation.AuthDtoCreator
import by.pilipuk.gateway.environment.data.dtoCreation.UserDtoCreator
import by.pilipuk.gateway.environment.data.entityCreation.UserCreator
import by.pilipuk.gateway.model.dto.UserDetailsDto
import by.pilipuk.gateway.model.entity.User
import org.springframework.stereotype.Service

@Service
class AuthTestService(
    private val userCreator: UserCreator,
    private val userDtoCreator: UserDtoCreator,
    private val authDtoCreator: AuthDtoCreator
) {

    fun createUserWriteDto(): UserWriteDto =
        userDtoCreator.createUserWriteDto()

    fun createUser(): User =
        userCreator.createUser()

    fun saveUser(): User =
        userCreator.saveUser(userCreator.createUser())

    fun createUserDetailsDto(): UserDetailsDto =
        userDtoCreator.createUserDetailsDto(createUser())

    fun createAuthRequest(): AuthRequest =
        authDtoCreator.createAuthRequest()

    fun createAuthResponse(): AuthResponse =
        authDtoCreator.createAuthResponse(createUserDetailsDto())
}