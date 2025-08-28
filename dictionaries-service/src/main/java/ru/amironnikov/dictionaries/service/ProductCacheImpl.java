package ru.amironnikov.dictionaries.service;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.amironnikov.dictionaries.dto.Product;
import ru.amironnikov.dictionaries.dto.ProductDto;
import ru.amironnikov.dictionaries.repository.ProductRepository;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Service
public class ProductCacheImpl implements ProductCache {

    private static final Logger logger = LoggerFactory.getLogger(ProductCacheImpl.class);

    private final Map<UUID, Product> cache = new ConcurrentHashMap<>();

    private final ProductRepository repository;

    public ProductCacheImpl(ProductRepository repository) {
        this.repository = repository;
    }

    @PostConstruct
    void init() {
        logger.debug("Init products cache");
        loadCache();
        logger.debug("Products cache init success");
    }

    @Override
    public List<Product> getProducts() {
        return cache.values().stream()
                .map(ProductDto::new)
                .collect(Collectors.toList());
    }

    @Override
    public Product get(UUID id) {
        return cache.get(id);
    }

    private void loadCache() {
        repository.findAll()
                .forEach(product -> cache.put(product.id(), product));
    }
}
