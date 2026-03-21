package by.pilipuk.gateway.business.repository

import by.pilipuk.gateway.core.exception.ApplicationException
import by.pilipuk.gateway.core.exception.ApplicationExceptionCode
import by.pilipuk.gateway.model.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.findByIdOrNull

interface UserRepository : JpaRepository<User, Long> {

    fun findByUsernameIgnoreCaseOrElseThrow(username: String): User {
        return findByUsernameIgnoreCase(username)
            ?: throw ApplicationException.create(ApplicationExceptionCode.NOT_FOUND_BY_USERNAME, username)
    }

    fun findByUsernameIgnoreCase(username: String) : User?

    fun findByIdOrElseThrow(id: Long) : User {
        return findByIdOrNull(id)
            ?: throw ApplicationException.create(ApplicationExceptionCode.NOT_FOUND_BY_ID, id)
    }
}