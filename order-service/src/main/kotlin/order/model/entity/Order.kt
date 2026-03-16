package by.pilipuk.order.model.entity

import by.pilipuk.commonCore.model.entity.BaseEntity
import by.pilipuk.order.model.enum.Status
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.OneToMany
import jakarta.persistence.Table

@Entity
@Table(name = "orders")
open class Order : BaseEntity() {

    @Column(name = "table_number")
    var tableNumber: Int = 0

    @Enumerated(EnumType.STRING)
    var status: Status = Status.CREATED

    @OneToMany(mappedBy = "order", cascade = [CascadeType.PERSIST])
    var items: MutableList<OrderItem> = mutableListOf()

}