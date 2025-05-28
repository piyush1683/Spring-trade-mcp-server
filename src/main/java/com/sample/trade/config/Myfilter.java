package com.sample.trade.config;

import java.io.IOException;

import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

@Component
public class Myfilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        // Perform actions before the request reaches the target
        System.out.println("Request received: " + request.getRemoteAddr());

        // Invoke the next filter in the chain (or the target servlet/controller)
        chain.doFilter(request, response);

        // Perform actions after the response is returned
        System.out.println("Response sent: " + response.getContentType());
    }
}