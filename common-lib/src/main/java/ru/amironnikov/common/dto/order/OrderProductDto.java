package ru.amironnikov.common.dto.order;

import ru.amironnikov.common.OrderProduct;

import java.util.UUID;

public record OrderProductDto(
        UUID product,
        int quantity
) implements OrderProduct {
}
