package by.pilipuk.gateway.business.service

import by.pilipuk.gateway.business.repository.UserRoleRepository
import by.pilipuk.gateway.model.entity.UserRole
import org.springframework.stereotype.Service

@Service
class UserRoleService(val userRoleRepository: UserRoleRepository) {

    fun setRole_USER_ROLE(): UserRole {
        return userRoleRepository.findByIdOrElseThrow(1);
    }
}