package by.pilipuk.gateway.model.entity

import by.pilipuk.commonCore.model.entity.BaseEntity
import by.pilipuk.gateway.model.enum.Role
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Table

@Entity
@Table(name = "roles", schema = "users")
class UserRole : BaseEntity() {

    @Enumerated(EnumType.STRING)
    var role: Role = Role.ROLE_USER
}