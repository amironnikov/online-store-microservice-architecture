package ru.amironnikov.order.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.amironnikov.order.repository.OrderReactiveRepository;

@Component
public class LoggingScheduler {

    private static final Logger logger = LoggerFactory.getLogger(LoggingScheduler.class);

    private final OrderReactiveRepository repository;

    public LoggingScheduler(OrderReactiveRepository repository) {
        this.repository = repository;
    }

    @Scheduled(fixedDelay = 60_000)
    void logDbState() {
        repository.countOrders()
                .subscribe(
                        count -> logger.debug("Orders count: {}", count)
                );
    }
}
