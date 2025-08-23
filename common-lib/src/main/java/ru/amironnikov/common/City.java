package ru.amironnikov.common;

import java.util.UUID;

public interface City {
    UUID id();

    String name();

    int code();

    int shippingCost();
}
