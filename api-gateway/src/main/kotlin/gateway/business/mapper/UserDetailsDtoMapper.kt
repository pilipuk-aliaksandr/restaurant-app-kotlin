package by.pilipuk.gateway.business.mapper

import by.pilipuk.gateway.model.dto.UserDetailsDto
import by.pilipuk.gateway.model.entity.User
import org.springframework.security.core.authority.SimpleGrantedAuthority

object UserDetailsDtoMapper {
    fun User.toUserDetailsDto() = UserDetailsDto(
        id = this.id,
        username = this.username,
        password = this.password,
        authorities = listOf(SimpleGrantedAuthority(this.userRole.role.name))
    )
}