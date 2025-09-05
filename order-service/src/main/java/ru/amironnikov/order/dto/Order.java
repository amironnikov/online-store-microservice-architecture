package ru.amironnikov.order.dto;

import ru.amironnikov.order.kafka.OrderStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface Order {
    UUID id();

    UUID userId();

    int zipCode();

    LocalDateTime created();

    LocalDateTime updated();

    OrderStatus status();

    int totalCost();

    List<? extends OrderProduct> products();

    double totalWeight();
}
