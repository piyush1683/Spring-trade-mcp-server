package com.sample.iban.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sample.iban.command.TradeAggregate;
import com.sample.iban.command.TradeDTOs.CreateTradeCommand;
import com.sample.iban.command.TradeDTOs.TradeBody;
import com.sample.iban.config.ConfigReader;

@RestController
public class TradeController {
    // This class is a placeholder for the TradeController implementation.
    // You can add methods and logic to handle trade-related requests here.
    // For example, you might want to implement methods to create, update, or
    // retrieve trades.
    @Autowired
    private ConfigReader configReader;

    @Autowired
    private TradeAggregate tradeAggregate;

    @GetMapping("/trade")
    public String getTradeInfo() {
        return "Trade information" + configReader.name() + "::" + configReader.proxy().host();
    }

    @PostMapping("/trade")
    public ResponseEntity createTradeAccount(@RequestBody TradeBody trade) {
        tradeAggregate.handle(new CreateTradeCommand(UUID.randomUUID(),
                trade.tradeId(), trade.party1(), trade.party2(), trade.amount(), trade.tradetype()));
        return ResponseEntity.ok("Trade account created: " + trade.toString());
    }

    @PutMapping("/trade/{id}")
    public String updateTradeAccount(@PathVariable String id, @RequestBody TradeBody account) {
        return "Trade account updated: " + account.toString();
    }

    @DeleteMapping("/trade/{id}")
    public String deleteTradeAccount(@PathVariable String id) {
        return "Trade account deleted with ID: " + id;
    }

}
