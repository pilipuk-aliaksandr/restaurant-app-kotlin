package by.pilipuk.kitchen.model.entity

import by.pilipuk.commonCore.model.entity.BaseEntity
import by.pilipuk.kitchen.model.enums.Status
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.OneToMany
import jakarta.persistence.Table

@Entity
@Table(name = "orders", schema = "kitchens")
class Order : BaseEntity() {

    @Column(name = "order_id")
    var orderId: Long? = null

    @Enumerated(EnumType.STRING)
    var status: Status = Status.ACCEPTED

    @OneToMany(mappedBy = "order", cascade = [CascadeType.PERSIST])
    var items: MutableList<OrderItem> = mutableListOf()
}