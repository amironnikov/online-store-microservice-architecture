package ru.amironnikov.image.service.impl;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import ru.amironnikov.image.service.ImageService;

import java.util.UUID;

@Service
public class ImageOffHeapServiceImpl extends ImageCommonService {

    public ImageOffHeapServiceImpl(ImageService minioService) {
        super(minioService);
    }

    @Override
    public void load(UUID productId, byte[] image) {
        minioService.load(productId, image);
    }

    @Override
    @Cacheable(value = "imagesOffHeapCache")
    public byte[] get(UUID productId) {
        return tryLoadFromMinio(productId);
    }
}
