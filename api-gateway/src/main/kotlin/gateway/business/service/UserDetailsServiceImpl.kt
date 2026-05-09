package by.pilipuk.gateway.business.service

import by.pilipuk.gateway.business.mapper.toUserDetailsDto
import by.pilipuk.gateway.business.repository.UserRepository
import by.pilipuk.gateway.model.dto.UserDetailsDto
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserDetailsServiceImpl(private val userRepository: UserRepository) : UserDetailsService {

    @Transactional(readOnly = true)
    override fun loadUserByUsername(username: String): UserDetailsDto {
        return userRepository.findByUsernameIgnoreCaseOrElseThrow(username).toUserDetailsDto()
    }
}