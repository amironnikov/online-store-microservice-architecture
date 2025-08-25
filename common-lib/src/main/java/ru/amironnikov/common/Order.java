package ru.amironnikov.common;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface Order {
    UUID getId();

    UUID userId();

    int zipCode();

    LocalDateTime created();

    LocalDateTime updated();

    List<? extends OrderProduct>  products();
}
