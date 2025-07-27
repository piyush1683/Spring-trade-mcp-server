package com.sample.trade.client;

import org.springframework.http.HttpHeaders;

import java.util.Arrays;
import java.util.List;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.sample.trade.command.TradeDTOs.TradeState;

public interface RestClient {

    public static void main(String[] args) {
        // Create a RestTemplate instance using RestTemplateBuilder
        RestTemplate restTemplate = new RestTemplateBuilder().build();
        String baseUrl = "http://localhost:8080/trades";
        // HttpEntity<String> requestEntity = new HttpEntity<String>("");
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Accept", "application/json");
        HttpEntity<String> requestEntity = new HttpEntity<String>("", headers);

        headers.set("Accept", "application/json");
        ResponseEntity<String> responseEntity = restTemplate.exchange(baseUrl,
                HttpMethod.GET, requestEntity, String.class);

        System.out.println("Response Status Code: " + responseEntity.getStatusCode());
        System.out.println("Response Headers: " + responseEntity.getHeaders());
        // System.out.println("Response Body: " + responseEntity.getHeaders());
        System.out.println("Response Body: " + responseEntity.getBody());

        ResponseEntity<List> getAllResponse = restTemplate.getForEntity(baseUrl, List.class);
        // List<TradeState> products = Arrays.asList(getAllResponse.getBody());
        // System.out.println("All Products: " + products);

    }
}
