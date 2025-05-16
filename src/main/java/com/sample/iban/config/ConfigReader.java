package com.sample.iban.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "app")
@Validated
public record ConfigReader(
        String name,
        String version,
        String description, Proxy proxy) {

    public record Proxy(
            String host,
            int port,
            String username,
            String password) {
        // No additional methods or fields needed
    }
    // No additional methods or fields needed

}
