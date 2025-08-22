package ru.amironnikov.dictionaries.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import ru.amironnikov.common.Product;

import java.util.UUID;

@Entity
@Table(name = "product")
public record ProductEntity(
        @Id
        UUID id,
        String name,
        String description,
        int cost,
        double weight,
        int guarantee
) implements Product {
}
