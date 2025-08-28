package ru.amironnikov.dictionaries.dto;

import java.util.UUID;

public record ProductDto(
        UUID id,
        String name,
        String description,
        int cost,
        double weight,
        int guarantee
) implements Product {
    public ProductDto(Product anotherProduct) {
        this(
                anotherProduct.id(),
                anotherProduct.name(),
                anotherProduct.description(),
                anotherProduct.cost(),
                anotherProduct.weight(),
                anotherProduct.guarantee()
        );
    }
}
