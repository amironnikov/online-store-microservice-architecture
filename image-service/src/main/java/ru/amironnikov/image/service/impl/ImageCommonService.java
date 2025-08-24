package ru.amironnikov.image.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.server.ResponseStatusException;
import ru.amironnikov.image.service.ImageService;

import java.util.UUID;

import static org.springframework.http.HttpStatus.NOT_FOUND;

public abstract class ImageCommonService implements ImageService {

    private static final Logger logger = LoggerFactory.getLogger(ImageOffHeapServiceImpl.class);

    protected ImageService minioService;

    public ImageCommonService(ImageService minioService) {
        this.minioService = minioService;
    }

    protected byte[] tryLoadFromMinio(UUID productId) {
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
