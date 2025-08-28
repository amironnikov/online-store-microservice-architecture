package ru.amironnikov.dictionaries.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.UUID;

@Entity
@Table(name = "product")
public class ProductEntity implements Product {

    @Id
    private UUID id;

    private String name;

    private String description;

    private int cost;

    private double weight;

    private int guarantee;

    public ProductEntity(
            UUID id,
            String name,
            String description,
            int cost,
            double weight,
            int guarantee) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.cost = cost;
        this.weight = weight;
        this.guarantee = guarantee;
    }

    public ProductEntity() {
    }

    @Override
    public UUID id() {
        return id;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public String description() {
        return description;
    }

    @Override
    public int cost() {
        return cost;
    }

    @Override
    public double weight() {
        return weight;
    }

    @Override
    public int guarantee() {
        return guarantee;
    }
}
