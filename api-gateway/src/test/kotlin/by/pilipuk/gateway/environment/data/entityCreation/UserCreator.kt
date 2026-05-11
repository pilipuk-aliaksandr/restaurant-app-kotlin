package by.pilipuk.gateway.environment.data.entityCreation

import by.pilipuk.gateway.business.repository.UserRepository
import by.pilipuk.gateway.business.repository.UserRoleRepository
import by.pilipuk.gateway.model.entity.User
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class UserCreator(
    private val passwordEncoder: PasswordEncoder,
    private val userRoleRepository: UserRoleRepository,
    private val userRepository: UserRepository
) {

    fun createUser(): User = User().apply {
        id = 1L
        username = "JohnDoe1"
        userRole = userRoleRepository.findByIdOrElseThrow(1L)
        password = passwordEncoder.encode("SuperPassword").toString()
        active = true
        createdAt = LocalDateTime.now()
    }

    fun saveUser(user: User): User {
        user.id = null
        return userRepository.save(user)
    }
}