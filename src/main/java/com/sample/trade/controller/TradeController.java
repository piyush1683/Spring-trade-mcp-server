package com.sample.trade.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sample.trade.command.TradeAggregate;
import com.sample.trade.command.TradeDTOs.CreateTradeCommand;
import com.sample.trade.command.TradeDTOs.TradeBody;
import com.sample.trade.command.TradeDTOs.TradeState;
import com.sample.trade.config.ConfigReader;
import com.sample.trade.repository.TradeRepositoryImpl;

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

    @Autowired
    private TradeRepositoryImpl tradeRepository;

    @GetMapping("/trades")
    @CrossOrigin(origins = "*")
    public ResponseEntity<List<TradeState>> getTradesInfo() {
        List<TradeState> tradeStateLst = tradeRepository.findallTrades();
        return ResponseEntity.ok().body(tradeStateLst);
    }

    @PostMapping("/trade")
    @CrossOrigin(origins = "*")
    public ResponseEntity createTradeAccount(@RequestBody TradeBody trade) {
        UUID tradeId = UUID.randomUUID();
        tradeAggregate.handle(new CreateTradeCommand(tradeId,
                trade.tradeId(), trade.party1(), trade.party2(), trade.amount(), trade.tradetype()));
        return ResponseEntity.ok("Trade account created: " + trade.toString());
    }

    @GetMapping("/trade/{id}")
    @CrossOrigin(origins = "*")
    public TradeState getTradeAccount(@PathVariable String id) {
        TradeState tradeState = tradeRepository.findTradeById(id);
        return tradeState;
    }

}
