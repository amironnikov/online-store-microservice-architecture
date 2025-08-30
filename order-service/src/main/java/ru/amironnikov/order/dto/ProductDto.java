package ru.amironnikov.order.dto;

import java.util.UUID;

public record ProductDto(
        UUID id,
        String name,
        String description,
        int cost,
        double weight,
        int guarantee
) {
}
