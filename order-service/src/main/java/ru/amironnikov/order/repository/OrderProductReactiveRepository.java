package ru.amironnikov.order.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import ru.amironnikov.order.entity.OrderProductEntity;

import java.util.UUID;

public interface OrderProductReactiveRepository extends ReactiveCrudRepository<OrderProductEntity, UUID> {
}
