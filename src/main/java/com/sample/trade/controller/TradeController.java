package com.sample.trade.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sample.trade.command.TradeDTOs.CreateTradeCommand;
import com.sample.trade.command.TradeDTOs.TradeBody;
import com.sample.trade.command.TradeDTOs.TradeState;
import com.sample.trade.config.ConfigReader;
import com.sample.trade.repository.TradeRepositoryImpl;
import com.sample.trade.service.TradeAggregate;

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

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/trades", method = RequestMethod.GET, produces = {
            "application/json" })
    public ResponseEntity<List<TradeState>> getTradesInfo() {
        var tradeStateLst = tradeRepository.findallTrades();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        // headers.setAccept("application/json");
        return ResponseEntity.ok().headers(headers).body(tradeStateLst);
    }

    @RequestMapping(value = "/trade", method = RequestMethod.POST, produces = {
            "application/json" })
    @CrossOrigin(origins = "*")
    public ResponseEntity<String> createTradeAccount(@RequestBody TradeBody trade) {
        UUID tradeId = UUID.randomUUID();
        tradeAggregate.handle(new CreateTradeCommand(tradeId,
                trade.tradeId(), trade.party1(), trade.party2(), trade.amount(), trade.tradetype()));
        return ResponseEntity.ok("Trade account created: " + tradeId.toString());
    }

    @RequestMapping(value = "/trade/{tradeid}", method = RequestMethod.GET, produces = {
            "application/json" })
    @CrossOrigin(origins = "*")
    public ResponseEntity<TradeState> getTradeAccount(@PathVariable("tradeid") String tradeid) {
        TradeState tradeState = tradeRepository.findTradeById(tradeid);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        // return tradeState;
        return ResponseEntity.ok().header("Trade-Id", tradeid).headers(headers)
                .body(tradeState);
    }

}
