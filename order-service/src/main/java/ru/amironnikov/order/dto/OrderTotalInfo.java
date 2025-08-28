package ru.amironnikov.order.dto;

public record OrderTotalInfo(
        int totalCost,
        int totalWeight
) {}
