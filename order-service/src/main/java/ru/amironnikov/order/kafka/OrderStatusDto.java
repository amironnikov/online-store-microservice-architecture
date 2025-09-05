package ru.amironnikov.order.kafka;

import java.util.UUID;

public record OrderStatusDto(
        UUID id,
        UUID userId,
        OrderStatus status,
        int totalCost
) {}
