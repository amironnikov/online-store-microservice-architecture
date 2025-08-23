package ru.amironnikov.common;

import java.util.List;
import java.util.UUID;

public interface Cart {
    UUID id();

    List<Product> products();

    int totalCost();
}
