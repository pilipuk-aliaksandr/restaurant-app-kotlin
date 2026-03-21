package by.pilipuk.gateway.model.entity

import by.pilipuk.commonCore.model.entity.BaseEntity
import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name = "users", schema = "users")
class User : BaseEntity() {

    var username: String = ""

    var password: String = ""

    @JoinColumn(name = "role_id")
    @ManyToOne(fetch = FetchType.EAGER, cascade = [CascadeType.MERGE])
    var userRole: UserRole = UserRole()
}