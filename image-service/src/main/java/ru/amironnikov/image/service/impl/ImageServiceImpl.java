package ru.amironnikov.image.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.amironnikov.image.service.ImageService;

import java.lang.ref.SoftReference;
import java.util.UUID;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class ImageServiceImpl implements ImageService {

    private static final Logger logger = LoggerFactory.getLogger(ImageServiceImpl.class);

    private final ImageService minioService;

    public ImageServiceImpl(ImageService minioService) {
        this.minioService = minioService;
    }

    @Override
    public void load(UUID productId, byte[] image) {
        var reference = new SoftReference<>(image);
        minioService.load(productId, image);
    }

    @Override
    @Cacheable(value = "imagesOffHeapCache")
    public byte[] get(UUID productId) {

        try {
            byte[] stored = minioService.get(productId);
            if (stored != null) {
                return stored;
            }
        } catch (Exception e) {
            logger.warn(e.getMessage());
            throw new ResponseStatusException(NOT_FOUND,
                    "Unable to find image by id: " + productId + "cause: " + e.getMessage());
        }

        logger.warn("Unable to find image by id: {}", productId);
        throw new ResponseStatusException(NOT_FOUND, "Unable to find image by id: " + productId);
    }
}
