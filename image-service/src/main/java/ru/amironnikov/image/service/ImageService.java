package ru.amironnikov.image.service;

import java.util.UUID;

public interface ImageService {
    void load(UUID productId, byte[] image);

    byte[] get(UUID productId);
}
