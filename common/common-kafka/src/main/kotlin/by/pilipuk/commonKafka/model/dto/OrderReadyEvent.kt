package by.pilipuk.commonKafka.model.dto

data class OrderReadyEvent(
    var orderId : Long = 0,
    var items: List<String> = listOf()
)
