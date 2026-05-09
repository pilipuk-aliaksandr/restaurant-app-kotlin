package by.pilipuk.gateway.business.service

import by.pilipuk.gateway.business.repository.UserRoleRepository
import by.pilipuk.gateway.model.entity.UserRole
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserRoleService(private val userRoleRepository: UserRoleRepository) {

    @Transactional(readOnly = true)
    fun setRole_USER_ROLE(): UserRole {
        return userRoleRepository.findByIdOrElseThrow(1);
    }
}