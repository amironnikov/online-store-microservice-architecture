package ru.amironnikov.order.service;

import productService.ProductOuterClass.Product;

import java.util.List;

public interface ProductGrpcService {
    List<Product> getProducts();
}
