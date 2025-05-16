package com.sample.iban.common;

import org.springframework.boot.SpringApplication;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Component
public class CommonErrorEventListner {
    Logger logger = LogManager.getLogger(SpringApplication.class);

    @EventListener
    public void HandleExceptions(Exception exception) {
        // Handle the error event
        System.out.println("Error occurred: " + exception.getMessage());
        logger.debug("Error occurred: " + exception.getMessage());
        // ... handle error ...
    }
}