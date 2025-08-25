package ru.amironnikov.order.service;

import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.amironnikov.common.dto.order.OrderDto;
import ru.amironnikov.common.dto.order.OrderListDto;

import java.util.UUID;

public interface OrderService {
    Mono<UUID> create(OrderDto order);
    Flux<OrderListDto> getAll(UUID userId);
}
