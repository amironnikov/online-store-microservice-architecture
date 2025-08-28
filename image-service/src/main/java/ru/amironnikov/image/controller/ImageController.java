package ru.amironnikov.image.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import ru.amironnikov.image.common.RestEndPoint;
import ru.amironnikov.image.common.StatusResponse;
import ru.amironnikov.image.openapi.OpenApi;
import ru.amironnikov.image.service.ImageService;

import java.io.IOException;
import java.util.UUID;

@RestController
public class ImageController implements OpenApi {

    private static final Logger logger = LoggerFactory.getLogger(ImageController.class);

    private final ImageService imageService;

    public ImageController(@Qualifier("imageOffHeapServiceImpl") ImageService imageService) {
        this.imageService = imageService;
    }

    @Override
    @PostMapping(value = RestEndPoint.IMAGES + "/{id}",
            consumes = MediaType.IMAGE_JPEG_VALUE)
    public StatusResponse loadImage(
            @PathVariable UUID id,
            @RequestBody byte[] imageBytes) throws IOException {
        logger.debug("Load image for product: {}, size: {}", id, imageBytes.length);
        imageService.load(id, imageBytes);
        logger.debug("Image for product: {} <Saved>", id);
        return StatusResponse.ok();
    }

    @GetMapping(
            value = RestEndPoint.IMAGES + "/{id}",
            produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] getImage(@PathVariable UUID id) {
        return imageService.get(id);
    }
}
