package ru.amironnikov.dictionaries.dto;



import java.util.List;

public record ProductsDto(
        List<Product> products
) {}
