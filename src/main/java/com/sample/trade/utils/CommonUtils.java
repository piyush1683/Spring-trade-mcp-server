package com.sample.trade.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;

public class CommonUtils {

    public static void main(String args[]) {
        // This is a placeholder for the main method.
        // In a real application, this could be used to run some utility functions
        // or tests.
        HashMap<String, String> map = new HashMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");
        map.put("key3", "value3");
        LocalDateTime currentDateTime = LocalDateTime.now();
        System.out.println("Current Date and Time222222: " + currentDateTime);

        map.forEach((key, value) -> {
            System.out.println("Key1111: " + key + ", Value1111: " + value);
        });
        System.out.println("CommonUtils main method executed.");
    }
}
