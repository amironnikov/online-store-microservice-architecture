package ru.amironnikov.dictionaries.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.amironnikov.common.RestEndPoint;
import ru.amironnikov.dictionaries.dto.ProductsDto;
import ru.amironnikov.dictionaries.service.ProductService;
import ru.amironnikov.dictionaries.swagger.ProductsOpenApi;

@RestController
public class ProductController implements ProductsOpenApi {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping(RestEndPoint.PRODUCTS)
    @Override
    public ProductsDto getProducts() {
        return new ProductsDto(productService.getProducts());
    }
}
