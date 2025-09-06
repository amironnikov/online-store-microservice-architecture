package ru.amironnikov.order.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.amironnikov.order.dto.OrderDto;
import ru.amironnikov.order.dto.OrderListDto;

import java.util.UUID;

public interface OrderService {
    Mono<UUID> create(OrderDto order);

    Flux<OrderListDto> getAll(UUID userId);

    Mono<Void> cancel(UUID orderId);
}
