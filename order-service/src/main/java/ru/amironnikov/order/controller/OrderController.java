package ru.amironnikov.order.controller;

import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import org.springframework.web.bind.annotation.*;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import ru.amironnikov.order.common.RestEndPoint;
import ru.amironnikov.order.dto.CreatedEntityResponse;
import ru.amironnikov.order.dto.OrderDto;
import ru.amironnikov.order.dto.OrderListDto;
import ru.amironnikov.order.dto.StatusResponse;
import ru.amironnikov.order.openapi.OrdersOpenApi;
import ru.amironnikov.order.service.OrderService;

import java.util.UUID;

@RestController
public class OrderController implements OrdersOpenApi {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    @PostMapping(RestEndPoint.ORDER)
    @RateLimiter(name = "rps_limiter_order_create")
    public Mono<CreatedEntityResponse> create(@RequestBody OrderDto order) {
        return orderService.create(order)
                .map(CreatedEntityResponse::new);
    }

    @Override
    @GetMapping(RestEndPoint.ORDER)
    @RateLimiter(name = "rps_limiter_all")
    public Flux<OrderListDto> getAll(@RequestParam UUID userId) {
        return orderService.getAll(userId);
    }

    @Override
    @PostMapping(RestEndPoint.ORDER + "/cancel" + "/{id}")
    @RateLimiter(name = "rps_limiter_cancel")
    public Mono<StatusResponse> cancel(@PathVariable UUID id) {
        return orderService.cancel(id)
                .map(arg -> StatusResponse.ok());
    }
}
