package ru.amironnikov.payment.dto;

import java.util.UUID;

public record OrderStatusDto(
        UUID id,
        UUID userId,
        OrderStatus status,
        int totalCost
) {}
