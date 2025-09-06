package ru.amironnikov.order.controller;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import ru.amironnikov.order.common.RestEndPoint;
import ru.amironnikov.order.dto.ProductDto;
import ru.amironnikov.order.openapi.ProductsOpenApi;
import ru.amironnikov.order.service.ProductGrpcService;

import java.util.Collections;
import java.util.UUID;

@RestController
public class ProductController implements ProductsOpenApi {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);

    private final ProductGrpcService grpcService;

    public ProductController(ProductGrpcService grpcService) {
        this.grpcService = grpcService;
    }

    @Override
    @GetMapping(RestEndPoint.PRODUCTS)
    @RateLimiter(name = "rps_limiter", fallbackMethod = "getFallbackProducts")
    @CircuitBreaker(name = "products_circuit_breaker", fallbackMethod = "getFallbackProducts")
    public Flux<ProductDto> getAllProducts() {
        try {
            return Flux.fromIterable(
                    grpcService.getProducts()
                            .stream()
                            .map(product -> new ProductDto(
                                    UUID.fromString(product.getId()),
                                    product.getName(),
                                    product.getDescription(),
                                    product.getCost(),
                                    product.getWeight(),
                                    product.getGuarantee()
                            )).toList()
            );
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return Flux.error(e);
        }
    }


    public Flux<ProductDto> getFallbackProducts(Throwable throwable) {
        logger.warn("Fallback triggered for getAllProducts: {}", throwable.getMessage());
        return Flux.fromIterable(
                Collections.emptyList()
        );
    }
}
