package by.pilipuk.order.model.entity

import by.pilipuk.commonCore.model.entity.BaseEntity
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name = "order_items")
open class OrderItem : BaseEntity() {

    @ManyToOne(cascade = [(CascadeType.MERGE)], fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false)
    var order: Order = Order()

    @Column(name = "item_name")
    var name: String = ""
}