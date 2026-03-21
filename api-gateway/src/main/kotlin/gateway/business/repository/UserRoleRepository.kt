package by.pilipuk.gateway.business.repository

import by.pilipuk.gateway.core.exception.ApplicationException
import by.pilipuk.gateway.core.exception.ApplicationExceptionCode
import by.pilipuk.gateway.model.entity.UserRole
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.findByIdOrNull

interface UserRoleRepository : JpaRepository<UserRole, Long> {

    fun findByIdOrElseThrow(id: Long): UserRole {
        return findByIdOrNull(id)
            ?: throw ApplicationException.create(ApplicationExceptionCode.NOT_FOUND_BY_ID, id)
    }
}