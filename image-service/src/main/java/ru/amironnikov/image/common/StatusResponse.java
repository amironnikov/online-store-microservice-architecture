package ru.amironnikov.image.common;

public record StatusResponse(
        String status
) {
    public static StatusResponse ok() {
        return new StatusResponse(
                "OK"
        );
    }
}
