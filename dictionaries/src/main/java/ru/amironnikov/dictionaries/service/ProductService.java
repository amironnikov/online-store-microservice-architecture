package ru.amironnikov.dictionaries.service;

import ru.amironnikov.common.Product;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    List<Product> getProducts();

    Product get(UUID id);
}
