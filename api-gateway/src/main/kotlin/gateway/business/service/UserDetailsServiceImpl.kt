package by.pilipuk.gateway.business.service

import by.pilipuk.gateway.business.mapper.UserDetailsDtoMapper.toUserDetailsDto
import by.pilipuk.gateway.business.repository.UserRepository
import by.pilipuk.gateway.model.dto.UserDetailsDto
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImpl(val userRepository: UserRepository) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetailsDto {
        return userRepository.findByUsernameIgnoreCaseOrElseThrow(username).toUserDetailsDto()
    }
}