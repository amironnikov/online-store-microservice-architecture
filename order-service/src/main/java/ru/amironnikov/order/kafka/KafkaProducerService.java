package ru.amironnikov.order.kafka;

public interface KafkaProducerService {
    void sendMessage(OrderStatusDto statusDto);
}