package ru.amironnikov.order.repository;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.amironnikov.order.entity.OrderEntity;

import java.util.UUID;

public interface OrderReactiveRepository extends ReactiveCrudRepository<OrderEntity, UUID> {
    @Query("select * from orders ord where user_id=:userId")
    Flux<OrderEntity> findAllOrders(UUID userId);

    @Query("select count(*) from orders ord")
    Mono<Long> countOrders();
}
