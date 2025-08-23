package ru.amironnikov.image.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.amironnikov.image.service.ImageService;

import java.lang.ref.SoftReference;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Service
public class ImageServiceImpl implements ImageService {

    private static final Logger logger = LoggerFactory.getLogger(ImageServiceImpl.class);

    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private final Lock readLock = lock.readLock();
    private final Lock writeLock = lock.writeLock();

    private final Map<UUID, SoftReference<byte[]>> localCache = new HashMap<>();

    private final ImageService minioService;

    public ImageServiceImpl(ImageService minioService) {
        this.minioService = minioService;
    }

    @Override
    public void load(UUID productId, byte[] image) {
        var reference = new SoftReference<>(image);
        localCache.put(productId, reference);
        minioService.load(productId, image);
    }

    @Override
    public byte[] get(UUID productId) {
        readLock.lock();
        try {
            SoftReference<byte[]> cached = localCache.get(productId);
            if (cached != null) {
                return cached.get();
            }
        } finally {
            readLock.unlock();
        }

        writeLock.lock();
        try {
            byte[] stored = tryLoadFromMinio(productId);
            localCache.put(productId, new SoftReference<>(stored));
            return stored;
        } finally {
            writeLock.unlock();
        }

    }

    private byte[] tryLoadFromMinio(UUID productId) {
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
