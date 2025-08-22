package ru.amironnikov.common;

import java.util.UUID;

public interface Country {

    UUID id();

    String name();

    int code();

    int shippingCost();
}
