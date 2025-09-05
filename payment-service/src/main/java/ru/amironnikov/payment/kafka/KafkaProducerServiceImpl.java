package ru.amironnikov.payment.kafka;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.amironnikov.payment.dto.OrderStatusDto;

@Service
public class KafkaProducerServiceImpl implements KafkaProducerService {

    private static final ObjectMapper mapper = new ObjectMapper();
    private static final Logger logger = LoggerFactory.getLogger(KafkaProducerServiceImpl.class);

    private final String topic;

    private final KafkaTemplate<String, JsonNode> kafkaTemplate;

    public KafkaProducerServiceImpl(
            @Value("${spring.kafka.producer.topic}") String topic,
            KafkaTemplate<String, JsonNode> kafkaTemplate) {
        this.topic = topic;
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void sendMessage(OrderStatusDto message) {
        kafkaTemplate.send(
                        topic,
                        message.id().toString(),
                        mapper.valueToTree(message)
                )
                .whenComplete((result, exception) -> {
                    if (exception != null) {
                        logger.error("An exception occurred: {}", exception.getMessage());
                    } else {
                        logger.debug("Message sent <success> id: {} to topic: {}", message.id(), topic);
                    }
                });
    }
}
