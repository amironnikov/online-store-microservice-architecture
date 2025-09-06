package ru.amironnikov.payment.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.amironnikov.payment.dto.OrderStatus;
import ru.amironnikov.payment.dto.OrderStatusDto;
import ru.amironnikov.payment.entity.UserAccount;
import ru.amironnikov.payment.repository.UserAccountRepository;

import java.util.Optional;
import java.util.UUID;

@Service
public class PaymentKafkaConsumer {

    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final Logger logger = LoggerFactory.getLogger(PaymentKafkaConsumer.class);

    private final UserAccountRepository repository;
    private final KafkaProducerService producerService;

    public PaymentKafkaConsumer(
            UserAccountRepository repository,
            KafkaProducerService producerService) {
        this.repository = repository;
        this.producerService = producerService;
    }

    @KafkaListener(
            topics = "${spring.kafka.consumer.topic}",
            groupId = "${spring.kafka.consumer.group-id}")
    @Transactional
    public void listen(ObjectNode message) throws JsonProcessingException {

        OrderStatusDto messageDto = objectMapper.treeToValue(message, OrderStatusDto.class);
        OrderStatus status = messageDto.status();

        if (status != OrderStatus.CREATED &&
                status != OrderStatus.CANCEL_REQUESTED) {
            return;
        }

        UUID userId = messageDto.userId();
        Optional<UserAccount> accountOpt = repository.findById(userId);
        if (accountOpt.isEmpty()) {
            logger.error("Incorrect user ID: {}", userId);
            return;
        }

        UserAccount account = accountOpt.get();

        if (status == OrderStatus.CREATED) {
            processCreated(messageDto,
                    account);
            return;
        }

        processCancelled(messageDto,
                account);
    }

    private void processCreated(OrderStatusDto messageDto,
                                UserAccount account) {

        int limit = account.getCreditLimit();

        if (limit < messageDto.totalCost()) {
            logger.warn("Not enough limit, user: {}", messageDto.userId());
            producerService.sendMessage(
                    new OrderStatusDto(
                            messageDto.id(),
                            messageDto.userId(),
                            OrderStatus.REJECTED,
                            messageDto.totalCost()
                    )
            );
            return;
        }

        account.setCreditLimit(account.getCreditLimit() - messageDto.totalCost());
        producerService.sendMessage(
                new OrderStatusDto(
                        messageDto.id(),
                        messageDto.userId(),
                        OrderStatus.COMPLETED,
                        messageDto.totalCost()
                )
        );
        logger.debug("Order paid success, user: {}, order: {}",
                account.getUserId(), messageDto.id());
    }

    private void processCancelled(OrderStatusDto messageDto,
                                  UserAccount account) {
        account.setCreditLimit(account.getCreditLimit() + messageDto.totalCost());
        producerService.sendMessage(
                new OrderStatusDto(
                        messageDto.id(),
                        messageDto.userId(),
                        OrderStatus.CANCELLED,
                        messageDto.totalCost()
                )
        );
        logger.debug("Cancel order success, user: {}, order: {}",
                account.getUserId(), messageDto.id());
    }

}