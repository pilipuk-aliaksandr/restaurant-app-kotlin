package by.pilipuk.gateway.business.service

import by.pilipuk.gateway.business.mapper.UserMapper.toEntity
import by.pilipuk.gateway.business.repository.UserRepository
import by.pilipuk.gateway.dto.UserWriteDto
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(val userRepository: UserRepository, val userRoleService: UserRoleService, val passwordEncoder: PasswordEncoder) {
    fun saveUser(userWriteDto: UserWriteDto) {
        userWriteDto.toEntity()
            .apply { password = passwordEncoder.encode(userWriteDto.password)!! }
            .apply { userRole = userRoleService.setRole_USER_ROLE() }
            .let { userRepository.save(it) }
    }

}