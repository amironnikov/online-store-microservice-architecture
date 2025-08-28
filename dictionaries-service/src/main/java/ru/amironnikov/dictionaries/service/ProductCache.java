package ru.amironnikov.dictionaries.service;


import ru.amironnikov.dictionaries.dto.Product;

import java.util.List;
import java.util.UUID;

public interface ProductCache {
    List<Product> getProducts();

    Product get(UUID id);
}
