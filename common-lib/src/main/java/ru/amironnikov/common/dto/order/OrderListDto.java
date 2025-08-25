package ru.amironnikov.common.dto.order;

import java.time.LocalDateTime;
import java.util.UUID;

public record OrderListDto(
        UUID id,
        int productsCount,
        int totalCost,
        int zipCode,
        LocalDateTime created,
        LocalDateTime updated
) {
}
