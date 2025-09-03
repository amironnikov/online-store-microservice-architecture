package ru.amironnikov.order.service.impl;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import productService.ProductOuterClass.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import ru.amironnikov.order.dto.OrderDto;
import ru.amironnikov.order.dto.OrderListDto;
import ru.amironnikov.order.entity.OrderEntity;
import ru.amironnikov.order.entity.OrderProductEntity;
import ru.amironnikov.order.repository.OrderProductReactiveRepository;
import ru.amironnikov.order.repository.OrderReactiveRepository;
import ru.amironnikov.order.service.OrderService;
import ru.amironnikov.order.service.ProductGrpcService;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    private final MeterRegistry meterRegistry;
    private Counter createOrderCounter;


    private final Map<String, Product> productsCostMap = new ConcurrentHashMap<>();

    private final OrderReactiveRepository orderRepository;
    private final OrderProductReactiveRepository productRepository;
    private final ProductGrpcService productGrpcService;

    public OrderServiceImpl(MeterRegistry meterRegistry, OrderReactiveRepository orderRepository,
                            OrderProductReactiveRepository productRepository,
                            ProductGrpcService productGrpcService) {
        this.meterRegistry = meterRegistry;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.productGrpcService = productGrpcService;
    }

    @PostConstruct
    void init() {
        logger.debug("Start init local cache of products");
        productGrpcService
                .getProducts()
                .forEach(product ->
                        productsCostMap.put(product.getId(), product)

                );
        createOrderCounter = meterRegistry.counter("online_store_create_order_total");
        logger.debug("Local cache products init <success>, size: {}", productsCostMap.size());
    }

    @Override
    @Transactional
    public Mono<UUID> create(OrderDto order) {

        logger.debug("Order create request with data: {}", order);


        int totalCost = totalCost(order);
        double totalWeight = totalWeight(order);

        createOrderCounter.increment();

        return orderRepository.save(
                new OrderEntity(order, totalCost, totalWeight)
        ).flatMap(
                createdOrder -> {
                    Flux<OrderProductEntity> products = Flux.fromIterable(order.products())
                            .map(product -> new OrderProductEntity(
                                    createdOrder.id(),
                                    product.product(),
                                    product.quantity()
                            ));

                    return productRepository.saveAll(products).
                            then(Mono.just(createdOrder.id()));
                }
        ).doOnSuccess(
                orderId -> logger.debug("Create order success, id: {}", orderId)
        );

    }

    @Override
    public Flux<OrderListDto> getAll(UUID userId) {
        return orderRepository.findAllOrders(userId).map(
                orderEntity -> new OrderListDto(
                        orderEntity.id(),
                        orderEntity.totalCost(),
                        orderEntity.totalWeight(),
                        orderEntity.zipCode(),
                        orderEntity.status(),
                        orderEntity.created(),
                        orderEntity.updated()
                )
        );
    }

    private int totalCost(OrderDto order) {
        return order.products()
                .stream()
                .mapToInt(product -> {
                    var prodInfo = productsCostMap.get(product.product().toString());
                    if (prodInfo == null) {
                        throw new IllegalArgumentException(
                                "Product with id: %s not found in cache"
                                        .formatted(product.product()));
                    }
                    return prodInfo.getCost() * product.quantity();
                }).sum();
    }

    private double totalWeight(OrderDto order) {
        return order.products()
                .stream()
                .mapToDouble(product -> {
                    var prodInfo = productsCostMap.get(product.product().toString());
                    if (prodInfo == null) {
                        throw new IllegalArgumentException(
                                "Product with id: %s not found in cache"
                                        .formatted(product.product()));
                    }
                    return prodInfo.getWeight() * product.quantity();
                }).sum();
    }
}
