package ru.amironnikov.order.controller;

import org.springframework.web.bind.annotation.*;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import ru.amironnikov.order.common.RestEndPoint;
import ru.amironnikov.order.dto.CreatedEntityResponse;
import ru.amironnikov.order.dto.OrderDto;
import ru.amironnikov.order.dto.OrderListDto;
import ru.amironnikov.order.dto.StatusResponse;
import ru.amironnikov.order.service.OrderService;

import java.util.UUID;

@RestController
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping(RestEndPoint.ORDER)
    public Mono<CreatedEntityResponse> create(@RequestBody OrderDto order) {
        return orderService.create(order)
                .map(CreatedEntityResponse::new);
    }

    @GetMapping(RestEndPoint.ORDER)
    public Flux<OrderListDto> getAll(@RequestParam UUID userId) {
        return orderService.getAll(userId);
    }

    @PostMapping(RestEndPoint.ORDER + "/cancel" + "/{id}")
    public Mono<StatusResponse> cancel(@PathVariable UUID id) {
        return orderService.cancel(id)
                .map(arg -> StatusResponse.ok());
    }
}
