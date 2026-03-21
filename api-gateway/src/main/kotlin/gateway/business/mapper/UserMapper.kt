package by.pilipuk.gateway.business.mapper

import by.pilipuk.gateway.dto.UserWriteDto
import by.pilipuk.gateway.model.entity.User

object UserMapper {

    fun UserWriteDto.toEntity() = User().apply {
        username = this@toEntity.username
        password = this@toEntity.password
    }
}