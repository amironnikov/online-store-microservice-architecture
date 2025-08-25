package ru.amironnikov.image;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ru.amironnikov.image.service.ImageService;
import ru.amironnikov.image.service.impl.ImageConcurrentMapServiceImpl;
import ru.amironnikov.image.service.impl.ImageOffHeapServiceImpl;
import ru.amironnikov.image.service.impl.ImageSoftReferenceServiceImpl;

import java.util.UUID;

@State(Scope.Benchmark)
public class ImageCachesBenchmark {

    private ImageService imageConcurrentMapServiceImpl;
    private ImageService imageOffHeapServiceImpl;
    private ImageService imageSoftReferenceServiceImpl;

    private byte[] fakeImage = new byte[10 * 1024 * 1024];
    private UUID productID = UUID.randomUUID();

    private ConfigurableApplicationContext context;

    @Setup(Level.Trial)
    public void setUp() {
        context = SpringApplication.run(ImageApplication.class);
        imageConcurrentMapServiceImpl = context.getBean(ImageConcurrentMapServiceImpl.class);
        imageOffHeapServiceImpl = context.getBean(ImageOffHeapServiceImpl.class);
        imageSoftReferenceServiceImpl = context.getBean(ImageSoftReferenceServiceImpl.class);
    }

    @TearDown
    public void tearDown() {
        context.close();
    }

    @Benchmark
    public void benchmarkOffHeap() {
       imageOffHeapServiceImpl.load(productID, fakeImage);
    }

    @Benchmark
    public void benchmarkConcurrentMap() {
        imageConcurrentMapServiceImpl.load(productID, fakeImage);
    }

    @Benchmark
    public void benchmarkReadWriteLock() {
        imageSoftReferenceServiceImpl.load(productID, fakeImage);
    }
}
