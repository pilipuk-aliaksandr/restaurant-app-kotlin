package by.pilipuk.commonKafka.model.entity

import by.pilipuk.commonCore.model.entity.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import org.hibernate.annotations.JdbcTypeCode
import org.hibernate.type.SqlTypes

@Entity
@Table(name = "outbox_events", schema = "common_outbox")
class OutboxEvent : BaseEntity() {

    var topic: String = ""

    @Column(name = "order_id")
    var orderId: String = ""

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "payload", columnDefinition = "jsonb", nullable = false)
    var payload: Any? = null
}