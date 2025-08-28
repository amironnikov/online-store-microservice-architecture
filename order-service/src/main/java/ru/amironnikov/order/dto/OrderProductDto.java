package ru.amironnikov.order.dto;

import java.util.UUID;

public record OrderProductDto(
        UUID product,
        int quantity
) implements OrderProduct {
}
