package by.pilipuk.commonKafka.core.config

import org.apache.kafka.clients.admin.NewTopic
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.TopicBuilder
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory

@Configuration
class KafkaConfig {

    @Bean
    fun kafkaTemplate(producerFactory: ProducerFactory<String, Any>): KafkaTemplate<String, Any> {
        return KafkaTemplate<String, Any>(producerFactory)
    }

    @Bean
    fun ordersTopic(): NewTopic {
        return TopicBuilder.name("orders")
            .partitions(3)
            .replicas(1)
            .build()
    }

    @Bean
    fun readyOrdersTopic(): NewTopic {
        return TopicBuilder.name("ready_orders")
            .partitions(3)
            .replicas(1)
            .build()
    }
}