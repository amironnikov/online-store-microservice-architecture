package ru.amironnikov.order.dto;

import java.util.List;
import java.util.UUID;

public record OrderDto(
        UUID id,
        UUID userId,
        List<OrderProductDto> products,
        int zipCode
) {
}
