package ru.amironnikov.order.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;
import ru.amironnikov.order.repository.OrderReactiveRepository;

@Service
public class OrderStatusKafkaConsumer {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger logger = LoggerFactory.getLogger(OrderStatusKafkaConsumer.class);

    private final OrderReactiveRepository repository;


    public OrderStatusKafkaConsumer(
            OrderReactiveRepository repository) {
        this.repository = repository;
    }

    @KafkaListener(
            topics = "${spring.kafka.consumer.topic}",
            groupId = "${spring.kafka.consumer.group-id}")
    @Transactional
    public Mono<Void> listen(ObjectNode message) throws JsonProcessingException {

        OrderStatusDto orderStatus = objectMapper.treeToValue(message, OrderStatusDto.class);

        if (orderStatus.status() == OrderStatus.CREATED) {
            return Mono.empty();
        }

        logger.debug("Received status for order: {}, status: {}",
                orderStatus.id(), orderStatus.status());

        repository.findById(orderStatus.id()).
                flatMap(
                        orderEntity -> {
                            orderEntity.setStatus(orderStatus.status());
                            return repository.save(orderEntity);
                        })
                .doOnSuccess(updatedEntity ->
                        logger.debug("Order status updated, order: {}, status: {}",
                                updatedEntity.id(), updatedEntity.status()))
                .doOnError(error ->
                        logger.error("Error processing order with id: {} : {}",
                                orderStatus.id(), error.getMessage()))
                .subscribe();

        return Mono.empty();
    }
}