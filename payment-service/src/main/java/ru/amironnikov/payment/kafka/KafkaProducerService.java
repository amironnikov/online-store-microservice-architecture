package ru.amironnikov.payment.kafka;

import ru.amironnikov.payment.dto.OrderStatusDto;

public interface KafkaProducerService {
    void sendMessage(OrderStatusDto statusDto);
}