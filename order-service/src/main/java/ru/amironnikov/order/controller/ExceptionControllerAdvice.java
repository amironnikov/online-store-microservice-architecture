package ru.amironnikov.order.controller;

import io.github.resilience4j.ratelimiter.RequestNotPermitted;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionControllerAdvice {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionControllerAdvice.class);

    @ResponseStatus(value = HttpStatus.TOO_MANY_REQUESTS)
    @ExceptionHandler(RequestNotPermitted.class)
    public void handleRequestNotPermitted(RequestNotPermitted e) {
        logger.warn(e.getMessage());
    }

}
