package ru.amironnikov.order.dto;

import java.util.UUID;

public interface OrderProduct {
    UUID product();

    int quantity();
}
