package by.pilipuk.commonKafka.business.repository

import by.pilipuk.commonKafka.model.entity.OutboxEvent
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface OutboxEventRepository : JpaRepository<OutboxEvent, Long> {

    @Query(
        value = """
        SELECT * FROM common_outbox.outbox_events
        WHERE active = true
        ORDER BY created_at ASC 
        LIMIT 1 
        FOR UPDATE SKIP LOCKED;
        """, nativeQuery = true
    )
    fun findNextForProcessing(): OutboxEvent?
}