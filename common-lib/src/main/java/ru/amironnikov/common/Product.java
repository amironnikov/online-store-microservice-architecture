package ru.amironnikov.common;

import java.util.UUID;

public interface Product {

    UUID id();

    String name();

    String description();

    int cost();

    double weight();

    int guarantee();
}
