package ru.amironnikov.order.kafka;

public enum OrderStatus {
    CREATED,
    COMPLETED,
    REJECTED,
    CANCEL_REQUESTED,
    CANCELLED,
}
