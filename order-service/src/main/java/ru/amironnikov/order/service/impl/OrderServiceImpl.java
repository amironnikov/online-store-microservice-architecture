package ru.amironnikov.order.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.amironnikov.common.dto.order.OrderDto;
import ru.amironnikov.common.dto.order.OrderListDto;
import ru.amironnikov.order.entity.OrderEntity;
import ru.amironnikov.order.entity.OrderProductEntity;
import ru.amironnikov.order.repository.OrderProductReactiveRepository;
import ru.amironnikov.order.repository.OrderReactiveRepository;
import ru.amironnikov.order.service.OrderService;

import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {

    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    private final OrderReactiveRepository orderRepository;
    private final OrderProductReactiveRepository productRepository;

    public OrderServiceImpl(OrderReactiveRepository orderRepository,
                            OrderProductReactiveRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    @Override
    @Transactional
    public Mono<UUID> create(OrderDto order) {

        logger.debug("Order create request with data: {}", order);

        return orderRepository.save(
                new OrderEntity(order)
        ).flatMap(
                createdOrder -> {
                    Flux<OrderProductEntity> products = Flux.fromIterable(order.products())
                            .map(product -> new OrderProductEntity(
                                    createdOrder.getId(),
                                    product.product(),
                                    product.quantity()
                            ));

                    return productRepository.saveAll(products).
                            then(Mono.just(createdOrder.getId()));
                }
        ).doOnSuccess(
                orderId -> logger.debug("Create order success, id: {}", orderId)
        );

    }

    @Override
    public Flux<OrderListDto> getAll(UUID userId) {
       return orderRepository.findAllOrders(userId).map(
                orderEntity -> new OrderListDto(
                        orderEntity.getId(),
                        10, //TODO
                        100, //TODO
                        orderEntity.zipCode(),
                        orderEntity.created(),
                        orderEntity.updated()
                )
        );
    }
}
