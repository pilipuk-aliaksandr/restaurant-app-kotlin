package by.pilipuk.gateway.environment.data.dtoCreation

import by.pilipuk.gateway.business.mapper.toUserDetailsDto
import by.pilipuk.gateway.dto.UserWriteDto
import by.pilipuk.gateway.model.dto.UserDetailsDto
import by.pilipuk.gateway.model.entity.User
import org.springframework.stereotype.Component

@Component
class UserDtoCreator {

        fun createUserWriteDto(): UserWriteDto = UserWriteDto(
            username = "JohnDoe1",
            password = "SuperPassword!"
        )

        fun createUserDetailsDto(user: User): UserDetailsDto =
            user.toUserDetailsDto()
}