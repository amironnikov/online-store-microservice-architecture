package ru.amironnikov.image.service.impl;

import io.minio.*;
import io.minio.errors.ErrorResponseException;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.amironnikov.image.exception.ImageNotFoundException;
import ru.amironnikov.image.service.ImageService;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.UUID;

@Service
public class MinioService implements ImageService {

    private static final Logger logger = LoggerFactory.getLogger(MinioService.class);

    private static final String DEFAULT_CONTENT_TYPE = "image/jpeg";
    private static final int BUFFER_SIZE = 1024;

    private final String bucketName;
    private final MinioClient minioClient;

    public MinioService(@Value("${minio.bucketName}") String bucketName,
                        MinioClient minioClient) {
        this.bucketName = bucketName;
        this.minioClient = minioClient;
    }

    @PostConstruct
    void init() {
        try {
            boolean found = minioClient.bucketExists(
                    BucketExistsArgs.builder().bucket(bucketName).build()
            );
            if (!found) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
                logger.debug("Bucket '{}' created successfully.", bucketName);
            } else {
                logger.debug("Bucket '{}' already exists.", bucketName);
            }
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void load(UUID productId, byte[] image) {
        try (InputStream inputStream = new ByteArrayInputStream(image)) {

            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(bucketName)
                            .object(productId.toString())
                            .stream(inputStream, image.length, -1)
                            .contentType(DEFAULT_CONTENT_TYPE)
                            .build());

        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public byte[] get(UUID productId) {
        try (InputStream stream = minioClient.getObject(
                GetObjectArgs.builder()
                        .bucket(bucketName)
                        .object(productId.toString())
                        .build())) {
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();

            int readed;
            byte[] data = new byte[BUFFER_SIZE];
            while ((readed = stream.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, readed);
            }

            buffer.flush();
            return buffer.toByteArray();
        }
        catch (ErrorResponseException e) {
            throw new ImageNotFoundException(e);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
