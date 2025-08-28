package ru.amironnikov.order.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record OrderListDto(
        UUID id,
        int totalCost,
        double totalWeight,
        int zipCode,
        OrderStatus status,
        LocalDateTime created,
        LocalDateTime updated
) {
}
