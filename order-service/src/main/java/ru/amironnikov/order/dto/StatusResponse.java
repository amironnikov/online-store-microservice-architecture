package ru.amironnikov.order.dto;

public record StatusResponse(
        String status
) {
    public static StatusResponse ok() {
        return new StatusResponse(
                "OK"
        );
    }
}
