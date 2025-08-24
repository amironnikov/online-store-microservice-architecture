package ru.amironnikov.image.service.impl;

import org.springframework.stereotype.Service;
import ru.amironnikov.image.service.ImageService;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ImageConcurrentMapServiceImpl extends ImageCommonService {

    private final Map<UUID, byte[]> localCache = new ConcurrentHashMap<>();

    public ImageConcurrentMapServiceImpl(ImageService minioService) {
        super(minioService);
    }

    @Override
    public void load(UUID productId, byte[] image) {
        localCache.put(productId, image);
        minioService.load(productId, image);
    }

    @Override
    public byte[] get(UUID productId) {
        byte[] cached = localCache.get(productId);
        if (cached != null) {
            return cached;
        }
        return tryLoadFromMinio(productId);
    }
}
