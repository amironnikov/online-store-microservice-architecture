package ru.amironnikov.dictionaries.dto;

import ru.amironnikov.common.Product;

import java.util.List;

public record ProductsDto(
        List<Product> products
) {}
