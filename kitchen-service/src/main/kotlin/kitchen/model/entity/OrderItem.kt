package by.pilipuk.kitchen.model.entity

import by.pilipuk.commonCore.model.entity.BaseEntity
import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name = "order_items", schema = "kitchens")
class OrderItem : BaseEntity() {

    @ManyToOne(fetch = FetchType.LAZY, cascade = [(CascadeType.MERGE)])
    @JoinColumn(name = "order_id")
    var order: Order = Order()

    @Column(name = "item_name")
    var name: String = ""

    @Column(name = "is_cooked")
    var cooked: Boolean = false
}